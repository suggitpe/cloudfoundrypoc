package org.suggs.cloudpoc.otherconsumer.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.suggs.cloudpoc.otherconsumer.trade.TradeEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TradeEventPactOtherConsumerTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("tradeevent_provider", this);

    @Pact(consumer = "othertradeevent_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws IOException {
        return builder
                .given("Trade with ID:1 exists").uponReceiving("Request for a deal event with an ID of 1, a domain of testDomain, and a version of 1")
                .path("/tradeEvent").method("GET").query("id=1&domain=testDomain&version=1")
                .willRespondWith().status(200).headers(createHeaders()).body(createTradeBody())
                .toPact();
    }

    @NotNull
    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private PactDslJsonBody createTradeBody() {
        return new PactDslJsonBody()
                .object("tradeIdentifier", createTradeIdentifier())
                .stringValue("eventType", "New")
                .stringValue("eventSubType", "New")
                .timestamp("executionTimestamp", DATE_TIME_FORMAT)
                .asBody();
    }

    private DslPart createTradeIdentifier() {
        return new PactDslJsonBody()
                .id("id", 1L)
                .stringValue("domain", "testDomain")
                .numberValue("version", 1);
    }

    @Test
    @PactVerification()
    public void checkWeCanProcessTheTradeEventPact() throws IOException {
        ResponseEntity<String> response = retrieveTradeData();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type")).contains("application/json");
        assertThat(response.getBody()).as("Response body is expected to be populated").isNotEmpty();

        TradeEvent tradeEvent = createTradeEventFromJson(response.getBody());
        assertThat(tradeEvent.getEventType()).isEqualTo("New");
        assertThat(tradeEvent.getEventSubType()).isEqualTo("New");
    }

    private ResponseEntity<String> retrieveTradeData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("domain", "testDomain");
        params.put("version", 1);

        return new RestTemplate().getForEntity(mockProvider.getUrl() + "/tradeEvent?id={id}&domain={domain}&version={version}", String.class, params);
    }

    private TradeEvent createTradeEventFromJson(String json) throws IOException {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        return mapper.readValue(json, TradeEvent.class);
    }

}

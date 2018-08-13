package org.suggs.cloudfoundry.greeting.dsl;

import lombok.extern.java.Log;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.abiities.CallAnApi;

@Log
public class TheReply {
    public static Question<String> content() {
        return actor ->
                CallAnApi.as(actor)
                        .getLastResponse()
                        .jsonPath()
                        .get("content").toString();
    }

    public static Question<String> name() {
        return actor ->
                CallAnApi.as(actor)
                        .getLastResponse()
                        .jsonPath()
                        .get("name").toString();


    }

    public static Question<String> location() {
        return actor ->
                CallAnApi.as(actor)
                        .getLastResponse()
                        .jsonPath()
                        .get("location").toString();
    }
}

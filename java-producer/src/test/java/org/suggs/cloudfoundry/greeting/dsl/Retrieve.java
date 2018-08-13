package org.suggs.cloudfoundry.greeting.dsl;

import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Retrieve {
    public static Performable client(int clientRef) {
        return instrumented(GetClientRef.class, clientRef);
    }
}

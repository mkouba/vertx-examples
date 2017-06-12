package io.vertx.examples.resteasy.helloworld;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Uppercaser {

    public String apply(String value) {
        return value.toUpperCase();
    }

}

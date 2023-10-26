package org.example.entity;

import java.util.List;

public record Row(List<String> values) {

    public String firstName() {
        return values.get(0);
    }

    public String lastName() {
        return values.get(1);
    }

    public String age() {
        return values.get(2);
    }

    public String job() {
        return values.get(3);
    }

    public String city() {
        return values.get(4);
    }

}

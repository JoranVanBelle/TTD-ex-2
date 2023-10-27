package org.example.entity;

import java.util.List;

public record Row(Person person) {

    public Person person() {
        return person;
    }

}

package org.example.entity;

public record Person(
        String firstName,
        String lastName,
        String age,
        String job,
        String city
) {
    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String age() {
        return age;
    }

    public String job() {
        return job;
    }

    public String city() {
        return city;
    }
}

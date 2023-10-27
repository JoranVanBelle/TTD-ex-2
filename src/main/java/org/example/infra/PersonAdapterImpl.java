package org.example.infra;

import org.example.Person;
import org.example.entity.Row;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PersonAdapterImpl implements PersonAdapter{

    private static final int NUMBER_OF_CONTENTS_ROW = 5;

    public Person getPerson(Row personString) {

        if(rowContentIsNotEqualToFive(personString)) {
            throw new IllegalArgumentException("number of values in a row is not correct...");
        }

        if(ageIsNotCorrect(personString)) {
            throw new IllegalArgumentException("age is not correct...");
        }

        return Person.newBuilder()
                .setFirstName(personString.personData().get(0))
                .setLastName(personString.personData().get(1))
                .setAge(Integer.parseInt(personString.personData().get(2)))
                .setJob(personString.personData().get(3))
                .setCity(personString.personData().get(4))
                .build();
    }

    private boolean rowContentIsNotEqualToFive(Row row) {
        return row.personData().size() != NUMBER_OF_CONTENTS_ROW;
    }

    private boolean ageIsNotCorrect(Row row) {
        return !Pattern.compile("\\d+").matcher(row.personData().get(2)).matches();
    }
}

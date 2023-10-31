package org.example.infra;

import org.example.Person;
import org.example.entity.Row;

public interface PersonAdapter {

    Person getPerson(Row personString);

}

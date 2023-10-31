package org.example.infra;

import org.example.entity.Row;

import java.io.InputStream;
import java.util.List;

public interface CsvScanner {

    List<Row> readCsv(InputStream in);

}

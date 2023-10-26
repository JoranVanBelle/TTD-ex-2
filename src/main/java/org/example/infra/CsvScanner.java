package org.example.infra;

import java.io.InputStream;
import java.util.List;

public interface CsvScanner {

    List<String> readCsv(InputStream in);

}

package org.example.infra;

import org.example.entity.Row;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

@Service
public class CsvScannerImpl implements CsvScanner {

    private Scanner scanner;
    private List<String> csvContent;

    public List<Row> readCsv(InputStream in) {

        return null;
    }
}

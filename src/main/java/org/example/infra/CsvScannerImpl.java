package org.example.infra;

import org.example.entity.Row;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class CsvScannerImpl implements CsvScanner {

    public List<Row> readCsv(InputStream in) {

        try {
            List<Row> csvContent = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            var line = reader.readLine();
            while((line = reader.readLine()) != null) {
                csvContent.add(createRow(line));
            }

            return csvContent;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Row createRow(String rowContent) {
        return new Row(Arrays.stream(rowContent.split(";")).toList());
    }
}

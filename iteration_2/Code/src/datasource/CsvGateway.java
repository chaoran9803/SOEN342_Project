package datasource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvGateway {

    public List<String[]> readRows(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        List<String[]> rows = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) {
                continue;
            }
            String[] row = lines.get(i).split(",", -1);
            for (int j = 0; j < row.length; j++) {
                row[j] = row[j].trim();
            }
            rows.add(row);
        }
        return rows;
    }

    public void writeRows(String fileName, String header, List<String[]> rows) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(header);
        for (String[] row : rows) {
            lines.add(String.join(",", row));
        }
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
    }
}

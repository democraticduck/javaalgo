import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CSVManager {
    public static List<Integer> readCSV(String filePath) {
        List<Integer> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(Integer.parseInt(values[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeInCSV(String filePath, Stream<String> stream) { //stream instead of list
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            stream.forEach(line -> {
            try {
                bw.write(line);
                bw.newLine();
            } catch (IOException e) {
                throw new UncheckedIOException(e); 
            }
        });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

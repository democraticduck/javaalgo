import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CSVManager {
    public static List<CustomType> readCSV(String filePath) {
        List<CustomType> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            System.out.println(filePath);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                data.add(new CustomType(Integer.parseInt(values[0]), values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<CustomType> readCSVSpecificLine(String filePath, int start, int end) {
        List<CustomType> data = new ArrayList<>();
        
        try (LineNumberReader br = new LineNumberReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null && br.getLineNumber() <= end) {
                
                if(br.getLineNumber() < start) continue;
                String[] values = line.split(",");
                
                data.add(new CustomType(Integer.parseInt(values[0]), values[1]));
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

    public static void writeStringInCSV(String filePath, String str, boolean toAppend) { //stream instead of list
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, toAppend))) {
            bw.write(str);
                bw.newLine();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomTypeArrStepCSV(String filePath, List<CustomType> arr, boolean toAppend) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, toAppend))) {
            bw.write("[");
            arr.forEach(elem -> {
            try {
                bw.write(String.valueOf(elem.value));
                bw.write("/");
                bw.write(elem.str);
                bw.write(", ");
            } catch (IOException e) {
                throw new UncheckedIOException(e); 
            }
        });
        bw.write("]");
        bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomTypeArrCSV(String filePath, List<CustomType> arr, boolean toAppend) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, toAppend))) {
            arr.forEach(elem -> {
            try {
                bw.write(String.valueOf(elem.value));
                bw.write(",");
                bw.write(elem.str);
                bw.newLine();
            } catch (IOException e) {
                throw new UncheckedIOException(e); 
            }
        });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomTypeStringCSV(String filePath, CustomType elem, int idx, boolean toAppend) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, toAppend))) {
            bw.write(String.valueOf(idx));
            bw.write(": ");
            bw.write(String.valueOf(elem.value));
            bw.write("/");
            bw.write(elem.str);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.*;
import java.io.*;

public class DataGenerator {
    
    private static String characters = "abcdefghijklmnopqrstuvwxyz";
    

    public static String generateFullLine(int length, int prefix) {
        StringBuilder sb = new StringBuilder(length + 16); 
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        sb.append(prefix).append(',');
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rand.nextInt(characters.length())));
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }

    public static Stream<String> getStream(int count, int lb, int ub) { //stream instead of list
        UniqueIntGenerator generator = new UniqueIntGenerator(count, lb, ub);
        
        Stream<String> stream = Stream.generate(() -> generateFullLine(4 + ThreadLocalRandom.current().nextInt(2), generator.getNextUnique())).limit(count);   
        return stream;
    }

    public static void generate(String filename, int num, int lb, int ub) {
        long startTime = System.nanoTime(); // Start timing
        Stream<String> stream = getStream(num, lb, ub);
        
        CSVManager.writeInCSV(filename, stream);
        
        long endTime = System.nanoTime(); // End timing
        long durationInNano = endTime - startTime;
        double durationInMillis = durationInNano / 1_000_000.0;

        System.out.printf("Data written to %s%n", filename);
        System.out.printf("Execution time: %.3f ms%n", durationInMillis);
    }
    
    public static void main(String[] args) {
        
        Stream<String> stream = getStream(60000000, 0, 2000000000);
        CSVManager.writeInCSV("dataset_sample_60000000.csv", stream);

        
        //List<CustomType> data = CSVManager.readCSVSpecificLine("dataset_sample_1000.csv", 1, 10);
        //for(CustomType i : data) System.out.printf("Value is %d/%s%n", i.value, i.str);
        
    }
    
}

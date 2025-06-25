import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class dataGenerator {

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rand.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static List<String[]> generateData(int count) {
        List<String[]> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int number = ThreadLocalRandom.current().nextInt(0, 1_000_000_010); // generate 1 and 1,000,000,010 (inclusive)
            String randomString = generateRandomString(5 + new Random().nextInt(3)); // 5-7 characters
            data.add(new String[]{String.valueOf(number), randomString});
        }
        return data;
    }

    public static void main(String[] args) {
    long startTime = System.nanoTime(); // Start timing

    List<String[]> dataset = generateData(1000);
    writeCSV.writeInCSV("dataset_sample_1000.csv", dataset);

    long endTime = System.nanoTime(); // End timing
    long durationInNano = endTime - startTime;
    double durationInMillis = durationInNano / 1_000_000.0;

    System.out.println("Data written to dataset_sample_1000.csv");
    System.out.printf("Execution time: %.3f ms%n", durationInMillis);
}

}

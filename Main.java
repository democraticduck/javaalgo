import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


interface SortingAlgorithm {
    List<CustomType> execute(String filename);
    void executeStep(String filename);
    
}

interface SearchingAlgorithm {
    boolean search(int i);
    boolean searchStep(int i, String filename);
    void searchExecute(String filename);
}

abstract class CustomTypeArraySortingAlgorithm implements SortingAlgorithm {
    protected List<CustomType> array;
    protected CustomTypeArraySortingAlgorithm(List<CustomType> arr) {
        this.array = arr;
    }
    public static long getTime() {
        return System.nanoTime();
    }
    public abstract List<CustomType> execute(String filename);
    public abstract void executeStep(String filename);
}
abstract class CustomTypeArraySearchingAlgorithm implements SearchingAlgorithm {
    protected List<CustomType> array;
    protected CustomTypeArraySearchingAlgorithm(List<CustomType> arr) {
        this.array = arr;
    }
    public static long getTime() {
        return System.nanoTime();
    }
    public abstract boolean search(int i);
    public abstract boolean searchStep(int i, String filename);
    public abstract void searchExecute(String filename);
}


class MergeSort extends CustomTypeArraySortingAlgorithm {
    private static List<CustomType> mergeSort(List<CustomType> arr, int i, int j) {
        
        if(j <= i) {
            List<CustomType> finArr = Arrays.asList(new CustomType[1]);
            if (j < i) {
                finArr.set(0, arr.get(j));
            }
            else 
                finArr.set(0, arr.get(i));
            return finArr;
        } 

        List<CustomType> finArr = Arrays.asList(new CustomType[j-i+1]);

        int mid = (j+i) / 2;
        List<CustomType> left = mergeSort(arr, i, mid);
        List<CustomType> right = mergeSort(arr, mid + 1, j);
        
        int lp = 0, rp = 0, idx = 0;
        while(lp < left.size() && rp < right.size()) {
            if (left.get(lp).value < right.get(rp).value) {
                finArr.set(idx, left.get(lp));
                lp++;
            }
            else {
                finArr.set(idx, right.get(rp));
                rp++;
            }    
            idx++;
        }

        while(lp < left.size()) {
            finArr.set(idx, left.get(lp));
            lp++;
            idx++;
        }
        while(rp < right.size()) {
            finArr.set(idx, right.get(rp));
            rp++;
            idx++;
        }
        return finArr;
    }
    private static List<CustomType> mergeSortStep(List<CustomType> arr, int i, int j, String filename) {
        List<CustomType> finArr = new ArrayList<>(j - i + 1);
        
        if(j <= i) {
            if (j < i) {
                finArr.add(arr.get(j));
            }
            else 
                finArr.add(arr.get(i));
            return finArr;
        } 

        int mid = (j+i) / 2;
        List<CustomType> left = mergeSortStep(arr, i, mid, filename);
        List<CustomType> right = mergeSortStep(arr, mid + 1, j, filename);
        
        int lp = 0, rp = 0;
        while(lp < left.size() && rp < right.size()) {
            
            if (left.get(lp).value < right.get(rp).value) {
                finArr.add(left.get(lp));
                lp++;
            }
            else {
                finArr.add(right.get(rp));
                rp++;
            }    
        }

        while(lp < left.size()) {
            finArr.add(left.get(lp));
            lp++;
        }
        while(rp < right.size()) {
            finArr.add(right.get(rp));
            rp++;
        }
        for(CustomType elem : finArr) {
            arr.set(i++, elem);
        }
        CSVManager.writeCustomTypeArrStepCSV(filename, arr, true);
        return finArr;
    }
    public List<CustomType> execute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        long curTime = System.nanoTime();
        List<CustomType> arr = mergeSort(array, 0, array.size() - 1); 
        long lastTime = System.nanoTime();
        
        System.out.println("Elapsed time for mergeSort (seconds): ");
        System.out.printf("%.3f%n", (lastTime-curTime)/1000000000.0);
        
        CSVManager.writeCustomTypeArrCSV(filename, arr, true);
        return arr;
    }

    public void executeStep(String filename) {
        CSVManager.writeCustomTypeArrStepCSV(filename, array, false); //write ori array
        mergeSortStep(array, 0, array.size() - 1, filename);
    }

    public MergeSort(List<CustomType> arr) {
        super(arr);
    }
}

class QuickSort extends CustomTypeArraySortingAlgorithm {
    private static void swap(List<CustomType> arr, int i, int j) {
        /*
        //1 if bit same, else 0, use xnor
        if(i == j) return;
        arr.set(j, ~(arr.get(i) ^ arr.get(j)));
        arr.set(i, ~(arr.get(i) ^ arr.get(j)));
        arr.set(j, ~(arr.get(i) ^ arr.get(j)));
        return;
        */
       CustomType tmp = arr.get(i);
       arr.set(i, arr.get(j));
       arr.set(j, tmp);
    }
    //Lomuto's partition
    private static void quickSortOld(List<CustomType> arr, int i, int j) {
        if(i >= j) return;

        int p = arr.get(j).value;
        int pos = i-1;
        for(int idx = i; idx < j; idx++) {
            if(arr.get(idx).value <= p) 
                swap(arr, ++pos, idx);
        }
        swap(arr, ++pos, j);
        quickSort(arr, i, pos-1);
        quickSort(arr, ++pos, j);
    }

    private static int partition(List<CustomType> arr, int l, int r) {
        CustomType p = arr.get(r);

        List<CustomType> le = new ArrayList<>(), eq = new ArrayList<>(), gr = new ArrayList<>();

        for(int i = l; i <= r; i++) {
            CustomType e = arr.get(i);
            if(arr.get(i).value < p.value)
                le.add(e);
            else if(arr.get(i).value == p.value)
                eq.add(e);
            else
                gr.add(e);
        }

        int idx = l;
        while(!le.isEmpty()) {
            arr.set(idx, le.remove(le.size() - 1));
            idx++;
        }
        int pi = idx;
        while(!eq.isEmpty()) {
            arr.set(idx, eq.remove(eq.size() - 1));
            idx++;
        }
        while(!gr.isEmpty()) {
            arr.set(idx, gr.remove(gr.size() - 1));
            idx++;
        }
        return pi;
    }

    private static void quickSort(List<CustomType> arr, int l, int r) {
        if(l < r) {
            int pi = partition(arr, l, r);
            quickSort(arr, l, pi-1);
            quickSort(arr, pi+1, r);
        }
    }
    
    private static void quickSortStep(List<CustomType> arr, int l, int r, String filename) {
        if(l < r) {
            int pi = partition(arr, l, r);
            CSVManager.writeCustomTypeArrStepPrefixCSV(filename, arr, String.format("pi=%d ", pi), true);
            quickSortStep(arr, l, pi-1, filename);
            quickSortStep(arr, pi+1, r, filename);
        }
    }

    private static void quickSortStepOld(List<CustomType> arr, int i, int j, String filename) {
        if(i >= j) return;

        int p = arr.get(j).value;
        int pos = i-1;
        for(int idx = i; idx < j; idx++) {
            if(arr.get(idx).value <= p) 
                swap(arr, ++pos, idx);
        }
        swap(arr, ++pos, j);
        CSVManager.writeCustomTypeArrStepCSV(filename, arr, true);
        quickSortStepOld(arr, i, pos-1, filename);
        quickSortStepOld(arr, ++pos, j, filename);
    }

    public List<CustomType> executeOld(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        long curTime = System.nanoTime();
        quickSortOld(array, 0, array.size() - 1); 
        long lastTime = System.nanoTime();
        
        System.out.println("Elapsed time for quick sort (seconds): ");
        System.out.printf("%.3f%n", (lastTime-curTime)/1000000000.0);

        CSVManager.writeCustomTypeArrCSV(filename, array, true);
        return array;
    }


    public List<CustomType> execute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        long curTime = System.nanoTime();
        quickSort(array, 0, array.size() - 1); 
        long lastTime = System.nanoTime();
        
        System.out.println("Elapsed time for quick sort (seconds): ");
        System.out.printf("%.3f%n", (lastTime-curTime)/1000000000.0);

        CSVManager.writeCustomTypeArrCSV(filename, array, true);
        return array;
    }

    public void executeStepOld(String filename) {
        CSVManager.writeCustomTypeArrStepCSV(filename, array, false); //write ori array
        quickSortStepOld(array, 0, array.size() - 1, filename);
    }

    public void executeStep(String filename) {
        CSVManager.writeCustomTypeArrStepCSV(filename, array, false); //write ori array
        quickSortStep(array, 0, array.size() - 1, filename);
    }

    public QuickSort(List<CustomType> arr) {
        super(arr);
    }
}

class BinarySearch extends CustomTypeArraySearchingAlgorithm {
    //assume array is sorted
    public BinarySearch(List<CustomType> arr) {
        super(arr);
    }

    private static boolean rec(List<CustomType> arr, int i, int j, int target) {
        if(i == j) return arr.get(j).value == target;
        int mid = (i+j) / 2;
        
        return arr.get(mid).value == target || 
                (arr.get(mid).value > target && rec(arr, i, mid, target)) || 
                (arr.get(mid).value < target && rec(arr, mid+1, j, target));

    }

    private static boolean nonRec(List<CustomType> arr, int target) {
        int l = 0, r = arr.size()-1;
        while(l <= r) {
            int k = (l+r)/2;
            if(arr.get(k).value == target) return true;
            if(arr.get(k).value > target) r = k-1;
            else l = k+1; 
        }
        return false;
    }

    private static boolean nonRecStep(List<CustomType> arr, int target, String filename) {
        int l = 0, r = arr.size()-1;
        while(l <= r) {
            int k = (l+r)/2;
            CSVManager.writeCustomTypeStringCSV(filename, arr.get(k), k, true);
            if(arr.get(k).value == target) return true;
            if(arr.get(k).value > target) r = k-1;
            else l = k+1; 
        }
        return false;
    }

    private static boolean recStep(List<CustomType> arr, int i, int j, int cnt, int target, String filename) {
        int mid = (i+j) / 2;
        CSVManager.writeCustomTypeStringCSV(filename, arr.get(mid), cnt, true);

        if(i == j) return arr.get(j).value == target;
        return arr.get(mid).value == target || 
                (arr.get(mid).value > target && recStep(arr, i, mid, cnt+1, target, filename)) || 
                (arr.get(mid).value < target && recStep(arr, mid+1, j, cnt+1, target, filename));
    }
    
    public boolean search(int i) {
        return rec(array, 0, array.size() - 1, i);
    }

    public void searchExecute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double avgsum = 0, bestsum = 0, worstsum = 0;
        
        //final int nIter = 30000000;
        
        for(int i = 0; i < array.size(); i++) { //worst case
            int val = (i%3 == 0) ? ThreadLocalRandom.current().nextInt(200000000) * -1 : ThreadLocalRandom.current().nextInt(200000001, Integer.MAX_VALUE);
            long t1 = System.nanoTime();
            search(val);
            long t2 = System.nanoTime();
            worstsum += (t2-t1)/1000000.0;
            //CSVManager.writeStringInCSV("log.txt", String.valueOf(t2-t1), true);
        }
        
        for(int i = 0; i < array.size(); i++) { //best case
            int mid = (array.size() - 1) / 2;
            int val = array.get(mid).value;
            long t1 = System.nanoTime();
            search(val);
            long t2 = System.nanoTime();
            bestsum += (t2-t1)/1000000.0;
        }
        for(int i = 0; i < array.size(); i++) { //avg case
            //int val = ThreadLocalRandom.current().nextInt(2000000000);
            long t1 = System.nanoTime();
            search(array.get(i).value);
            long t2 = System.nanoTime();
            avgsum += (t2-t1)/1000000.0;
            //CSVManager.writeStringInCSV("log1.txt", String.valueOf(t2-t1), true);
        }
        


        double sz = array.size();
        //System.out.printf("Highest: %.6f, lowest: %.6f%n", highest, lowest);
        CSVManager.writeStringInCSV(filename, String.format("Best Time: %.6fms%nAverage time: %.6fms%nWorst Time: %.6fms", bestsum/sz, avgsum/sz, worstsum/sz), true);
    }

    public void searchNonRec(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double avgsum = 0, bestsum = 0, worstsum = 0;
        
        for(int i = 0; i < array.size(); i++) { //worst case
            int val = (i%3 == 0) ? ThreadLocalRandom.current().nextInt(200000000) * -1 : ThreadLocalRandom.current().nextInt(200000001, Integer.MAX_VALUE);
            long t1 = System.nanoTime();
            nonRec(array, val);
            long t2 = System.nanoTime();
            worstsum += (t2-t1)/1000000.0;
        }
        for(int i = 0; i < array.size(); i++) { //best case
            int mid = (array.size() - 1) / 2;
            int val = array.get(mid).value;
            long t1 = System.nanoTime();
            nonRec(array, val);
            long t2 = System.nanoTime();
            bestsum += (t2-t1)/1000000.0;
        }

        for(int i = 0; i < array.size(); i++) { //avg case
            //int val = ThreadLocalRandom.current().nextInt(2000000000);
            int val = array.get(i).value;
            long t1 = System.nanoTime();
            nonRec(array, val);
            long t2 = System.nanoTime();
            avgsum += (t2-t1)/1000000.0;
        }

        double sz = array.size();
        //System.out.printf("Highest: %.6f, lowest: %.6f%n", highest, lowest);
        CSVManager.writeStringInCSV(filename, String.format("Best Time: %.6fms%nAverage time: %.6fms%nWorst Time: %.6fms", bestsum/sz, avgsum/sz, worstsum/sz), true);
    }
    public boolean searchStepOld(int i, String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        boolean found = recStep(array, 0, array.size() - 1, 0, i, filename);
        String s = found ? "Found element" : "Element not found";
        CSVManager.writeStringInCSV(filename, s, true);
        return found;
    }

    public boolean searchStep(int i, String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        boolean found = nonRecStep(array, i, filename);
        String s = found ? "Found element" : "Element not found";
        CSVManager.writeStringInCSV(filename, s, true);
        return found;
    }
}


public class Main {
    public static boolean sortvalidator(List<CustomType> arr) {
        for(int i = 1; i < arr.size(); i++) {
            if(arr.get(i).value < arr.get(i-1).value) return false;
        }

        return true;
    }

    public static boolean searchvalidator(List<CustomType> arr, int target) {
        for(int i = 0; i < arr.size(); i++)
            if(arr.get(i).value == target) return true;

        return false; 
    }

    


    public static void main(String[] args) {
        /*
        //search step
        int target = 1999999440;
        List<CustomType> arr = CSVManager.readCSV("merge_sort_5000000.csv");
        CustomTypeArraySearchingAlgorithm binarySearchAlgo = new BinarySearch(arr);
        System.out.println(binarySearchAlgo.searchStep(target, String.format("binary_search__step_%d.txt", target)));
        */
        /*
        //sort steps
        int start = 0, end = 10;
        List<CustomType> arr = CSVManager.readCSVSpecificLine("dataset_sample_5000000.csv", start, end);
        CustomTypeArraySortingAlgorithm algo1 = new MergeSort(new ArrayList<>(arr));
        algo1.executeStep(String.format("merge_sort_step_%d_%d.txt", start, end));

        CustomTypeArraySortingAlgorithm algo2= new QuickSort(arr);
        algo2.executeStep(String.format("quick_sort_step_%d_%d.txt", start, end));
        */
        List<CustomType> arr = CSVManager.readCSV("dataset_sample_5000000.csv");
        /*
        CustomTypeArraySortingAlgorithm algo1 = new MergeSort(arr);
        algo1.execute("merge_sort_5000000.csv");
        */
        CustomTypeArraySortingAlgorithm algo2= new QuickSort(arr);
        algo2.execute("quick_sort_5000000.csv");
        /*
        //individual sort
        */
        /*
        //loop sort
        for(int i = 5000000; i <= 50000000; i+=5000000) {
            List<CustomType> arr = CSVManager.readCSV(String.format("dataset_sample_%d.csv", i));
            CustomTypeArraySortingAlgorithm algo1 = new MergeSort(arr);
            algo1.execute(String.format("merge_sort_%d.csv", i));

            CustomTypeArraySortingAlgorithm algo2 = new QuickSort(arr);
            algo2.execute(String.format("quick_sort_%d.csv", i));
            System.out.println(" ");
        }
        */
       /*
       //loop search
        for(int i = 5000000; i <= 50000000; i+=5000000) {
            List<CustomType> arr = CSVManager.readCSV(String.format("merge_sort_%d.csv", i));
            BinarySearch binarySearchAlgo = new BinarySearch(arr);
            binarySearchAlgo.searchNonRec(String.format("binary_search_%d.txt", i));
            System.out.println(" ");
        }
        */
        /*
        //individual search
        List<CustomType> arr = CSVManager.readCSV("merge_sort_5000000.csv");
        BinarySearch binarySearchAlgo = new BinarySearch(arr);
        binarySearchAlgo.searchNonRec("binary_search_5000000.txt");
        */
        //IntArrayBasedAlgorithm algo = new MergeSort(it.boxed().toList());
        


        /*
        CustomTypeArraySortingAlgorithm mergeSortAlgo = new MergeSort(arr);
        CustomTypeArraySortingAlgorithm quickSortAlgo = new QuickSort(arr);
        mergeSortAlgo.executeStep();
        quickSortAlgo.executeStep();
        //mergeSort doesnt do inplace sorting, whereas quicksort does, so put quicksort after mergesort
        long curTime1 = getTime();
        mergeSortAlgo.executeStep();
        long lastTime1 = getTime();
        
        long curTime2 = getTime();
        quickSortAlgo.executeStep();
        long lastTime2 = getTime();

        long timeElapsed1 = lastTime1 - curTime1;
        long timeElapsed2 = lastTime2 - curTime2;
        System.out.println("Elapsed time for mergeSort: ");
        System.out.println(timeElapsed1 / 1000);
        System.out.println("Elapsed time for quickSort: ");
        System.out.println(timeElapsed2 / 1000);
        //System.out.println("Is valid or not: " + sortvalidator(newa));
       */
    }
}
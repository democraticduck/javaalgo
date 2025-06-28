import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
class MyThread extends Thread {
    public static long getTime() {
        return System.currentTimeMillis();
    }
    public void run() {
        long st = getTime();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(st - getTime());
    }
}



interface SortingAlgorithm {
    List<CustomType> execute(String filename);
    void executeStep(String filename);
    
}

interface SearchingAlgorithm {
    
    boolean search(int i);
    boolean searchStep(int i, String filename);
    void searchExecute(List<CustomType> arr, String filename);
}

abstract class CustomTypeArraySortingAlgorithm implements SortingAlgorithm {
    protected List<CustomType> array;
    protected CustomTypeArraySortingAlgorithm(List<CustomType> arr) {
        this.array = arr;
    }
    public static double getTime() {
        return System.currentTimeMillis() / 1000;
    }
    public abstract List<CustomType> execute(String filename);
    public abstract void executeStep(String filename);
}
abstract class CustomTypeArraySearchingAlgorithm implements SearchingAlgorithm {
    protected List<CustomType> array;
    protected CustomTypeArraySearchingAlgorithm(List<CustomType> arr) {
        this.array = arr;
    }
    public static double getTime() {
        return System.currentTimeMillis() / 1000;
    }
    public abstract boolean search(int i);
    public abstract boolean searchStep(int i, String filename);
    public abstract void searchExecute(List<CustomType> arr, String filename);
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
        double curTime1 = getTime();
        List<CustomType> arr = mergeSort(array, 0, array.size() - 1); 
        double lastTime1 = getTime();
        
        System.out.println("Elapsed time for mergeSort: ");
        System.out.println(lastTime1-curTime1);
        
        CSVManager.writeCustomTypeArrCSV(filename, arr, true);
        return arr;
    }

    public void executeStep(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
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
    private static void quickSort(List<CustomType> arr, int i, int j) {
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

    private static void quickSortStep(List<CustomType> arr, int i, int j, String filename) {
        if(i >= j) return;

        int p = arr.get(j).value;
        int pos = i-1;
        for(int idx = i; idx < j; idx++) {
            if(arr.get(idx).value <= p) 
                swap(arr, ++pos, idx);
                
            
        }
        swap(arr, ++pos, j);
        CSVManager.writeCustomTypeArrStepCSV(filename, arr, true);
        quickSortStep(arr, i, pos-1, filename);
        quickSortStep(arr, ++pos, j, filename);
    }

    public List<CustomType> execute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double curTime1 = getTime();
        quickSort(array, 0, array.size() - 1); 
        double lastTime1 = getTime();
        
        System.out.println("Elapsed time for quick sort: ");
        System.out.println(lastTime1-curTime1);

        CSVManager.writeCustomTypeArrCSV(filename, array, true);
        return array;
    }

    public void executeStep(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
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

    private static boolean recStep(List<CustomType> arr, int i, int j, int cnt, int target, String filename) {
        int mid = (i+j) / 2;
        CSVManager.writeCustomTypeStringCSV(filename, arr.get(mid), cnt, true);

        if(i == j) return arr.get(j).value == target;
        return arr.get(mid).value == target || 
                (arr.get(mid).value > target && recStep(arr, i, mid, cnt+1, target, filename)) || 
                (arr.get(mid).value < target && recStep(arr, mid+1, j, cnt+1, target, filename));
    }
    
    public boolean search(int i) {
        return rec(array, 0, array.size(), i);
    }

    public void searchExecute(List<CustomType> arr, String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double lowest = Integer.MAX_VALUE, highest = -1;
        double sum = 0;
        for(CustomType e : arr) {
            double t1 = getTime();
            search(e.value);
            double t2 = getTime();
            lowest = Math.min(t2-t1, lowest);
            highest = Math.max(t2-t1, highest);
            sum += (t2-t1);
        }
        CSVManager.writeStringInCSV(filename, String.format("Best Time: %f%nAverage time: %f%nWorst Time: %f", lowest, sum/arr.size(), highest), true);
    }

    public boolean searchStep(int i, String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        boolean found = recStep(array, 0, array.size(), 0, i, filename);
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
        
        List<CustomType> arr = CSVManager.readCSV("dataset_sample_70000000.csv");
        CustomTypeArraySortingAlgorithm algo1 = new MergeSort(arr);
        algo1.execute("merge_sort_70000000.csv");

        CustomTypeArraySortingAlgorithm algo2= new QuickSort(arr);
        algo2.execute("quick_sort_70000000.csv");
        
        /*
        for(int i = 10000000; i <= 70000000; i+=10000000) {
            List<CustomType> arr = CSVManager.readCSV(String.format("dataset_sample_%d.csv", i));
            CustomTypeArraySortingAlgorithm algo1 = new MergeSort(arr);
            algo1.execute(String.format("merge_sort_%d.csv", i));

            CustomTypeArraySortingAlgorithm algo2 = new QuickSort(arr);
            algo2.execute(String.format("quick_sort_%d.csv", i));
        }
        */
        /*
        List<CustomType> arr = CSVManager.readCSV("merge_sort_sorted.csv");
        CustomTypeArraySearchingAlgorithm binarySearchAlgo = new BinarySearch(arr);
        binarySearchAlgo.searchExecute(arr, "binary_search_stats.txt");
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
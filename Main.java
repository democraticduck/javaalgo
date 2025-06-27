import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
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
    List<Integer> execute();
}

interface SearchingAlgorithm {
    boolean search(int i);
}

abstract class IntArraySortingAlgorithm implements SortingAlgorithm {
    protected List<Integer> array;
    protected IntArraySortingAlgorithm(List<Integer> arr) {
        this.array = arr;
    }
    public abstract List<Integer> execute();
}

abstract class IntArraySearchingAlgorithm implements SearchingAlgorithm {
    protected List<Integer> array;
    protected IntArraySearchingAlgorithm(List<Integer> arr) {
        this.array = arr;
    }
    public abstract boolean search(int i);
}

class MergeSort extends IntArraySortingAlgorithm {
    private static List<Integer> mergeSort(List<Integer> arr, int i, int j) {
        List<Integer> finArr = new ArrayList<>();
        
        if(j <= i) {
            if (j < i) {
                finArr.add(arr.get(j));
            }
            else 
                finArr.add(arr.get(i));
            return finArr;
        } 

        int mid = (j+i) / 2;
        List<Integer> left = mergeSort(arr, i, mid);
        List<Integer> right = mergeSort(arr, mid + 1, j);
        
        int lp = 0, rp = 0;
        while(lp < left.size() && rp < right.size()) {
            
            if (left.get(lp) < right.get(rp)) {
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
        return finArr;
    }
    public List<Integer> execute() {
        return mergeSort(array, 0, array.size() - 1);
    }
    public MergeSort(List<Integer> arr) {
        super(arr);
    }
}

class QuickSort extends IntArraySortingAlgorithm {
    private static void swap(List<Integer> arr, int i, int j) {
        //1 if bit same, else 0, use xnor
        if(i == j) return;
        arr.set(j, ~(arr.get(i) ^ arr.get(j)));
        arr.set(i, ~(arr.get(i) ^ arr.get(j)));
        arr.set(j, ~(arr.get(i) ^ arr.get(j)));
        return;
    }
    //Lomuto's partition
    private static void quickSort(List<Integer> arr, int i, int j) {
        if(i >= j) return;

        int p = arr.get(j);
        int pos = i-1;
        for(int idx = i; idx < j; idx++) {
            if(arr.get(idx) <= p) 
                swap(arr, ++pos, idx);
        }
        swap(arr, ++pos, j);
        quickSort(arr, i, pos-1);
        quickSort(arr, ++pos, j);
    }

    public List<Integer> execute() {
        quickSort(array, 0, array.size() - 1);
        return array;
    }

    public QuickSort(List<Integer> arr) {
        super(arr);
    }
}

class BinarySearch extends IntArraySearchingAlgorithm {
    //assume array is sorted
    public BinarySearch(List<Integer> arr) {
        super(arr);
    }

    private static boolean rec(List<Integer> arr, int i, int j, int target) {
        if(i == j) return arr.get(j) == target;
        int mid = (i+j) / 2;
        
        return arr.get(mid) == target || 
                (arr.get(mid) > target && rec(arr, i, mid, target)) || 
                (arr.get(mid) < target && rec(arr, mid+1, j, target));

    }
    
    public boolean search(int i) {
        return rec(array, 0, array.size(), i);
    }
}




public class Main {
    public static boolean sortvalidator(List<Integer> arr) {
        for(int i = 1; i < arr.size(); i++) {
            if(arr.get(i) < arr.get(i-1)) return false;
        }

        return true;
    }

    public static boolean searchvalidator(List<Integer> arr, int target) {
        for(int i = 0; i < arr.size(); i++)
            if(arr.get(i) == target) return true;

        return false; 
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        Random r = new Random();
            
        IntStream it = r.ints(20000000, 0, 200000000);
        
        List<Integer> arr = CSVManager.readCSV("dataset_sample_1000.csv");
        IntArraySortingAlgorithm mergeSortAlgo = new MergeSort(arr);
        IntArraySortingAlgorithm quickSortAlgo = new QuickSort(arr);
        //IntArrayBasedAlgorithm algo = new MergeSort(it.boxed().toList());

        //mergeSort doesnt do inplace sorting, whereas quicksort does, so put quicksort after mergesort
        long curTime1 = getTime();
        mergeSortAlgo.execute();
        long lastTime1 = getTime();
        
        long curTime2 = getTime();
        quickSortAlgo.execute();
        long lastTime2 = getTime();

        long timeElapsed1 = lastTime1 - curTime1;
        long timeElapsed2 = lastTime2 - curTime2;
        System.out.println("Elapsed time for mergeSort: ");
        System.out.println(timeElapsed1 / 1000);
        System.out.println("Elapsed time for quickSort: ");
        System.out.println(timeElapsed2 / 1000);
        //System.out.println("Is valid or not: " + sortvalidator(newa));
       
    }
}
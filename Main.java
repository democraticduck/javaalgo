import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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
    void searchExecute(String filename);
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
    public static double getTimeMillis() {
        return System.currentTimeMillis();
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
        double curTime1 = getTime();
        List<CustomType> arr = mergeSort(array, 0, array.size() - 1); 
        double lastTime1 = getTime();
        
        System.out.println("Elapsed time for mergeSort: ");
        System.out.printf("%.6f", lastTime1-curTime1);
        
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

    public List<CustomType> executeOld(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double curTime1 = getTime();
        quickSort(array, 0, array.size() - 1); 
        double lastTime1 = getTime();
        
        System.out.println("Elapsed time for quick sort: ");
        System.out.println(lastTime1-curTime1);

        CSVManager.writeCustomTypeArrCSV(filename, array, true);
        return array;
    }


    public List<CustomType> execute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double curTime1 = getTime();
        quickSort(array, 0, array.size() - 1); 
        double lastTime1 = getTime();
        
        System.out.println("Elapsed time for quick sort: ");
        System.out.printf("%.6f", lastTime1-curTime1);

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
        return rec(array, 0, array.size() - 1, i);
    }

    public void searchExecute(String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        double lowest = Double.MAX_VALUE, highest = -1;
        double sum = 0;
        
        //final int nIter = 30000000;
        
        for(int i = 0; i < array.size(); i++) {
            //int val = ThreadLocalRandom.current().nextInt(2000000000);
            double t1 = getTimeMillis();
            search(array.get(i).value);
            double t2 = getTimeMillis();
            lowest = Math.min(t2-t1, lowest);
            highest = Math.max(t2-t1, highest);
            sum += (t2-t1);
        }
        
        CSVManager.writeStringInCSV(filename, String.format("Best Time: %.6fms%nAverage time: %.6fms%nWorst Time: %.6fms", lowest, sum/array.size(), highest), true);
    }

    public boolean searchStep(int i, String filename) {
        CSVManager.writeStringInCSV(filename, "", false); //reset file
        boolean found = recStep(array, 0, array.size() - 1, 0, i, filename);
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
        List<CustomType> arr = CSVManager.readCSV("dataset_sample_50000000.csv");
        CustomTypeArraySortingAlgorithm algo1 = new MergeSort(arr);
        algo1.execute("merge_sort_50000000.csv");

        CustomTypeArraySortingAlgorithm algo2= new QuickSort(arr);
        algo2.execute("quick_sort_50000000.csv");
        */
        /*
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
        for(int i = 5000000; i <= 50000000; i+=5000000) {
            List<CustomType> arr = CSVManager.readCSV(String.format("merge_sort_%d.csv", i));
            CustomTypeArraySearchingAlgorithm binarySearchAlgo = new BinarySearch(arr);
            binarySearchAlgo.searchExecute(String.format("binary_search_%d.txt", i));
            System.out.println(" ");
        }
        */
        List<CustomType> arr = CSVManager.readCSV("merge_sort_40000000.csv");
        CustomTypeArraySearchingAlgorithm binarySearchAlgo = new BinarySearch(arr);
        binarySearchAlgo.searchExecute("binary_search_40000000.txt");
        /*
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
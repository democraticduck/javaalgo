import java.util.Random;
import java.util.stream.IntStream;
import java.util.ArrayList;
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


class Sorter {
    public static ArrayList<Integer> mergeSort(int[] arr, int i, int j) {
        ArrayList<Integer> finArr = new ArrayList();
        
        if(j <= i) {
            if (j < i) {
                finArr.add(arr[j]);
            }
            else 
                finArr.add(arr[i]);
            return finArr;
        } 

        int mid = (j+i) / 2;
        ArrayList<Integer> left = mergeSort(arr, i, mid);
        ArrayList<Integer> right = mergeSort(arr, mid + 1, j);
        
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

}



public class Main {
    public static long getTime() {
        return System.currentTimeMillis();
    }
    public static void main(String[] args) {
    Random r = new Random();
        
    IntStream it = r.ints(40000000, 0, 200000000);
    int[] arr = it.toArray(); //amke sure unique
    
    long curTime = getTime();
    ArrayList<Integer> newa = Sorter.mergeSort(arr, 0, arr.length - 1);
    long lastTime = getTime();
    long timeElapsed = lastTime - curTime;
    System.out.println("Elapsed time: ");
    System.out.println(timeElapsed / 1000);

       
    }
}
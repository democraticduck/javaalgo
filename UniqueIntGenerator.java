import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.concurrent.ThreadLocalRandom;
import java.util.PrimitiveIterator;
import java.util.BitSet;

class UniqueIntGenerator {

    private BitSet bs;
    private int num = 10;
    private int lb = 0;
    private int ub = 10;
    
    private PrimitiveIterator.OfInt it;

    public UniqueIntGenerator(int n, int l, int u) {
        this.num = (n >= 0) ? n : 10;
        this.lb = (l >= 0) ? l : 0;
        this.ub = (u >= 0) ? u : 10;
        this.bs = new BitSet(num);

        IntStream is = ThreadLocalRandom.current().ints(this.lb, this.ub+1);
        this.it = is.iterator();
    }
    
    public boolean checkVal(int val) {
        return this.bs.get(val);
    }
    
    public boolean insertVal(int val) {
        if(!checkVal(val)) {
            this.bs.set(val, true);
            return true;
        }
        return false;
    }

    public List<Integer> genRandList() {
        List<Integer> array = new ArrayList<Integer>(); 
        int n = 0;
        while(n < num) {
            int elem = this.it.next();
            while(!insertVal(elem))
                elem = this.it.next();
            n++;
            array.add(elem);
        }
        return array;
    }

    public int getNextUnique() {        
        int elem = this.it.next();
        while(!insertVal(elem))
            elem = this.it.next();
        
        return elem;
    }

    /*
    public static void main(String[] args) {
        UniqueIntGenerator gen = new UniqueIntGenerator(10, 1, 10);
        for(int i = 0; i < 10; i++) {
            System.out.println(gen.getNextUnique());
        }
    }*/
}
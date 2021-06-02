import edu.princeton.cs.algs4.In;

import java.util.concurrent.atomic.AtomicLong;

public class Inversion {
    private static Integer[] elems;
    private static Integer[] aux;

    public Inversion(String filename, int n) {
        elems = new Integer[n];
        aux = new Integer[n];
        In in = new In(filename);
        for (int i = 0; i < elems.length; i++) {
            elems[i] = in.readInt();
        }
        in.close();
    }

    public static void main(String[] args) {
        Inversion inversion = new Inversion("IntegerArray.txt", 100000);
        System.out.println(inversion.countInversions());
    }

    private static long merge(int lo, int mid, int hi) {
        // a[lo....mid] and a[mid+1 .... hi] are sorted sub arrays
        int i = lo, j = mid + 1;
        if (hi + 1 - lo >= 0) System.arraycopy(elems, lo, aux, lo, hi + 1 - lo);
        AtomicLong inversions = new AtomicLong(0);
        for (int k = lo; k <= hi; k++)
            if (i > mid) elems[k] = aux[j++];
            else if (j > hi) elems[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) {
                inversions.addAndGet((mid - i + 1));
                elems[k] = aux[j++];
            } else elems[k] = aux[i++];
        return inversions.get();
    }

    private static long countInversions(int lo, int mid, int hi) {
        int n = hi - lo + 1;
        if (n == 0 || n == 1) return 0L;
        long leftInversions = countInversions(lo, lo + (mid - lo) / 2, mid);
        long rightInversions = countInversions(mid + 1, mid + 1 + (hi - mid - 1) / 2, hi);
        long splitInversions = merge(lo, mid, hi);
        return leftInversions + rightInversions + splitInversions;
    }

    public long countInversions() {
        int lo = 0;
        int hi = elems.length - 1;
        int mid = lo + (hi - lo) / 2;
        return countInversions(lo, mid, hi);
    }
}

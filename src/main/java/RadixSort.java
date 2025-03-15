import java.util.Arrays;

public abstract class RadixSort {
    public void radixSort(int[] array){
            int max = Arrays.stream(array).max().orElse(0);
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countSort(array, exp);
            }

    }
    abstract void countSort(int[] array, int exp);
}

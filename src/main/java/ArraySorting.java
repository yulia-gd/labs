import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ArraySorting {

    private static final RadixSort radixSort = new ParallelRadixSort();

    public static void main(String[] args) {
        int[] array = null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("1 - Генерувати дані, -1 - Зчитати з файлу: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.print("Введіть кількість елементів у масиві: ");
            int size = scanner.nextInt();
            array = generateRandomArray(size);
            saveArrayToFile(array, "output/input.txt");
        } else if (choice == -1) {
            array = readArrayFromFile("output/numbers.txt");
            System.out.println("Розмір зчитаного масиву: "+array.length);
        }

        if (array != null) {
            long startSortTime = System.currentTimeMillis();

            radixSort.radixSort(array);

            long endSortTime = System.currentTimeMillis();

            saveArrayToFile(array, "output/result.txt");

            System.out.println("Час сортування: " + (endSortTime - startSortTime) / 1000.0 + " сек");
        }
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000);
        }
        return array;
    }

    private static void saveArrayToFile(int[] array, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int count = 0;
            for (int num : array) {
                writer.write(num + " ");
                count++;
                if (count == 1000) {
                    writer.newLine();
                    count = 0;
                }
            }
            if (count > 0) {
                writer.newLine();
            }
            System.out.println("Масив збережено у файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
        }
    }


    private static int[] readArrayFromFile(String fileName) {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    list.add(Integer.parseInt(token));
                }
            }
            System.out.println("Масив зчитано з файлу: " + fileName);
        } catch (IOException e) {
            System.err.println("Помилка зчитування з файлу: " + e.getMessage());
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}

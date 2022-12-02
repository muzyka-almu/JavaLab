import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
    }

    private static void task1()  throws IOException {
        System.out.println("Введите значения разделенные пробелом:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();

        String[] numbers = str.split("\\s+");

        ArrayList<Integer> elements = new ArrayList<Integer>();
        Set<Integer> set = new HashSet<>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Map<Integer, Integer> distribution = new HashMap<Integer, Integer>();
        int max = Integer.parseInt(numbers[0]);
        int min = max;
        int sum = 0;
        int multiply = 1;
        int sumSquaresNegativeValues = 0;
        for (String numberString: numbers) {
            int value = Integer.parseInt(numberString);
            set.add(value);
            elements.add(value);

            if (value > max) {
                max = value;
            }

            if (value < min) {
                min = value;
            }

            sum += value;
            multiply *= value;

            Integer meetTimes = map.get(value);
            map.put(value, meetTimes == null ? 1 : meetTimes + 1);

            if (value < 0) {
                sumSquaresNegativeValues += value * value;
            }

            Integer distributionMeetTimes = map.get(numberString.length());
            distribution.put(numberString.length(), distributionMeetTimes == null ? 1 : distributionMeetTimes + 1);
        }

        if (set.size() == 1) {
            System.out.println("Равны");
        } else {
            System.out.println("Не равны");
        }

        System.out.println("Максимальное: " + max);
        System.out.println("Минимальное: " + min);
        System.out.println("Сумма: " + sum);
        System.out.println("Произведение: " + multiply);

        int maxRepeatCount = Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
        Object[] modValues = map.entrySet().stream().filter(x -> x.getValue() == maxRepeatCount).map(Map.Entry::getKey).toArray();
        if (modValues.length == 1) {
            System.out.println("Мода последовательности: " + modValues[0]);
        } else {
            System.out.println("Нет мода последовательности");
        }

        System.out.println("Сумма квадратов отрицательных чисел: " + sumSquaresNegativeValues);
        System.out.println("Размаха последовательности: " + (max - min));

        System.out.println("Введите значение N для вывода чисел в случайном порядке:");
        String strN = reader.readLine();
        int n = Integer.parseInt(strN);
        System.out.print("Чисел в случайном порядке: ");
        for (int i=0; i<n; i++) {
            int random_int = (int)Math.floor(Math.random()*(map.size()));
            System.out.print(numbers[random_int] + ' ');
        }
        System.out.println();

        Object[] valuesArr = map.keySet().toArray();
        Arrays.sort(valuesArr);
        double median;
        if (valuesArr.length % 2 == 0) {
            median = ((int)valuesArr[valuesArr.length/2] + (int)valuesArr[valuesArr.length/2 - 1])/2;
        } else {
            median = (int) valuesArr[valuesArr.length/2];
        }
        System.out.println("Медиана: " + median);

        Object[] uniqueValues = map.entrySet().stream().filter(x -> x.getValue() == 1).map(Map.Entry::getKey).toArray();
        System.out.println("Уникальные числа: " + Arrays.toString(uniqueValues));


        System.out.print("Распределение: ");
        for (Object distributionKey: distribution.keySet().toArray()) {
            int value = distribution.get(distributionKey);
            System.out.print(distributionKey + " " + value + "-циферных чисел. ");
        }
        System.out.println();

        System.out.println("Процент выше среднего: " + findAboveAveragePercentage(sum / elements.size(), elements.toArray(new Integer[0])) + "%");
        rep(reader);
        System.out.println("Среднее значение: " + (double)sum / elements.size());

        Object[] reversElements = elements.toArray();
        Arrays.sort(reversElements, Collections.reverseOrder());
        System.out.println("Обратная сортировка: " + Arrays.toString(reversElements));
    }

    private static void rep(BufferedReader reader) throws IOException {
        System.out.println("Введите значение начальное значение последовательности:");
        String startN = reader.readLine();
        int start = Integer.parseInt(startN);

        System.out.println("Введите значение конечное значение последовательности:");
        String endN = reader.readLine();
        int end = Integer.parseInt(endN);

        System.out.println("Введите значение шаг последовательности:");
        String stepN = reader.readLine();
        int step = Integer.parseInt(stepN);


        ArrayList<Integer> elements = new ArrayList<Integer>();
        int item = start;
        while (item <= end) {
            elements.add(item);
            item += step;
        }
        System.out.println("Последовательность: " + Arrays.toString(elements.toArray()));
    }

    private static int findAboveAveragePercentage(Integer average, Integer[] elements) {
        Object[] arr = Arrays.stream(elements).filter(x -> x > average).toArray();

        return 100 * arr.length / elements.length;
    }

    private static int findExtraElement(Integer[] elements) {
        Arrays.sort(elements);
        int maxDif = 0;
        for (int i=1; i < elements.length; i++) {
            if (maxDif < elements[i] - elements[i-1]) {
                maxDif = elements[i] - elements[i-1];
            }
        }
        for (int i=1; i < elements.length; i++) {
            if (maxDif == elements[i] - elements[i-1]) {
                if (i > elements.length / 2) {
                    return elements[i];
                } else {
                    return elements[i-1];
                }
            }
        }
        System.out.println(Arrays.toString(elements));

        return 1;
    }
}
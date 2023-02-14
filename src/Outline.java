import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Outline {

    public static List<String> getList() {
        return List.of("hi", "bat", "ear", "hello", "iguana",
                "beaver", "winterland", "elephant", "eye", "qi");
    }

    // Loop through the words and print each one on a separate line,
    // with two spaces in front of each word.
    public static void question1() {
        List<String> words = getList();
        System.out.println("1: ");
        words.stream().forEach(word -> System.out.println("  " + word));
    }

    // Repeat this problem but without two spaces in front of each word.
    // This should be trivial if you use the same approach as the previous
    // question; the point here is to make use of a method reference.
    public static void question2() {
        List<String> words = getList();
        System.out.println("2: ");
        words.forEach(StreamUtils::printWord);
    }

    // For each of the following lambda expressions (see Question 5 in Worksheet 2),
    // produce the list that contains the elements of the original list
    // that satisfy the predicate defined by the lambda expression
    // (use the filter stream operation):
    //  - s -> s.length() < 4 (strings with no more than 3 characters),
    //  -  s -> s.contains("b") (strings containing "b"),
    // s -> (s.length() % 2) == 0 (strings of even length).

    public static void question3() {
        List<String> words = getList();
        System.out.println("3:");
        System.out.println(words.stream().filter(s -> s.length() < 4).toList());
        System.out.println(words.stream().filter(s -> s.contains("b")).toList());
        System.out.println(words.stream().filter(s -> (s.length() % 2) == 0).toList());
    }


    // For each of the following lambda expressions (see Question 7 in Worksheet 2),
    // produce the list that contains the results of applying the function
    // defined by the lambda expression to each element of the original list
    // (use the map stream operation):
    // - s -> s + "!",
    //  s -> s.replace("i", "eye"),
    //  s -> s.toUpperCase().

    public static void question4() {
        List<String> words = getList();
        System.out.println("4:");
        System.out.println(
                words.stream().map(s -> s + "!").toList()
        );
        System.out.println(
                words.stream().map(s -> s.replace("i", "eye")).toList()
        );
        System.out.println(
                words.stream().map(s -> s.toUpperCase()).toList()
        );
    }


    // (*) Turn the strings in the list into uppercase, keep only the
    // ones that are shorter than four characters, and, of what is remaining,
    // keep only the ones that contain "e", and print the first result.
    // Repeat the process, except checking for a "q" instead of an "e".

    public static void question5() {
        List<String> words = getList();
        System.out.println("5a:");
        System.out.println(words.stream()
                .map(String::toUpperCase)
                .filter(s -> s.length() < 4)
                .filter(s -> s.contains("E"))
                .toList()
                .get(0));
    }


    // (** ) The above example uses lazy evaluation, but it is not easy to see
    // that it is doing so. Create a variation of the above example that shows
    // that it is doing lazy evaluation. The simplest way is to track which
    // entries are turned into upper case.

    public static void question6() {
        List<String> words = getList();
        System.out.println("6:");
        Optional<String> word = words.stream()
                .map(s -> s.toUpperCase())
                .peek(s -> System.out.println("pre-filter 1: " + s))
                .filter(s -> s.length() < 4)
                .peek(s -> System.out.println("pre-filter 2: " + s))
                .filter(s -> s.contains("E"))
                .findFirst();

        System.out.println(word.get());
    }

    // (*) Produce a single String that is the result of concatenating the
    // uppercase versions of all the Strings.
    // For example, the result should be "HIHELLO...".
    // Hint: use a map operation that turns the words into upper case,
    // followed by a reduce operation that concatenates them.

    public static void question7() {
        List<String> words = getList();
        System.out.println("7:");
        Optional<String> str = words.stream()
                .map(s -> s.toUpperCase())
                .reduce((String s, String result) -> {
                    result += s;
                    return result;
                });
        System.out.println(str.get());
    }


    // (*) Produce a single String that is the result of concatenating the
    // uppercase versions of all the Strings.
    // For example, the result should be "HIHELLO...".
    // Use a single reduce operation, without using map.

    public static void question8() {
        List<String> words = getList();
        System.out.println("8:");
        Optional<String> str = words.stream()
                .reduce((String s, String result) -> {
                    result += s;
                    return result.toUpperCase();
                });
        System.out.println(str.get());
    }

    // (*) Produce a String that is all the words concatenated together, but
    // with commas in between. For example, the result should be "hi,hello,...".
    // Note that there is no comma at the beginning, before "hi", and also no comma
    // at the end, after the last word.

    public static void question9() {
        List<String> words = getList();
        System.out.println("9:");
        String str = words.stream().collect(Collectors.joining(", "));
        System.out.println(str);
    }

    // Use streams to filter the first two meat dishes
    public static void question10() {
        List<Dish> menu = Dish.getMenu();
        List<Dish> meat = menu.stream()
                .limit(2)
                .filter(dish -> dish.type() == Dish.Type.MEAT)
                .collect(Collectors.toList());
        System.out.println(meat);
    }

    // 11. Count the number of dishes in a stream using the map and reduce methods.
    public static void question11() {
        long count = Dish.getMenu().stream().count();
        System.out.println(count);
    }

    public static Integer[] getIntegerArray() {
        return new Integer[]{1, 7, 3, 4, 8, 2};
    }

    public static void question12() {
        Integer[] ints = getIntegerArray();
        System.out.println(Arrays.stream(ints).map(number -> number * number).collect(Collectors.toList()));
    }

//  13. Given two lists of numbers, print out all pairs of numbers. For example,
//    given a list `[1, 2, 3]` and a list `[3, 4]` you should print:
//    `[[1, 3], [1, 4], [2, 3], [2, 4], [3, 3], [3, 4]]`.
//    For simplicity, you can represent each *pair* as a list with two elements.

    public static void question13() {
        System.out.println("13:");
        List<Integer> list1 = List.of(5, 2, 3);
        List<Integer> list2 = List.of(3, 4);

        List<List<Integer>> output = list1.stream()
                .flatMap(num1 -> list2.stream()
                        .map(num2 -> List.of(num1, num2)))
                .collect(Collectors.toList());

        System.out.println(output);

    }

    public static void question14() {
        System.out.println("14:");
        List<Integer> list1 = List.of(5, 2, 3);
        List<Integer> list2 = List.of(3, 4);

        List<List<Integer>> output = list1.stream()
                .flatMap(num1 -> list2.stream()
                        .filter(num2 -> (num1 + num2) % 3 == 0)
                        .map(num2 -> List.of(num1, num2)))
                .collect(Collectors.toList());

        System.out.println(output);

    }

    public static void question15() {
        System.out.println("15: ");
        List<Integer> list = List.of(2,3,4,5);

        Optional<Integer> output = list.stream().reduce((curr, acc) -> acc += curr);
        System.out.println(output.get());

        int output2 = list.stream().reduce(0, Integer::sum);
        System.out.println(output2);

        int output3 = list.stream().mapToInt(n -> n).sum();
        System.out.println(output3);
    }

    public static List<Double> randomNumberList(Integer someSzie) {
//        List<Integer> output = new ArrayList<>(someSzie);
//        return output.stream().map(Random::new).mapToInt(Random::nextInt).collect(Collectors.toList());
//        return new Random().ints(someSzie).boxed().toList();
        return Stream.generate(new Random()::nextDouble)
                .limit(someSzie)
                .collect(Collectors.toList());
    }


    public static void main(String... args) { // varargs alternative to String[]
        System.out.println(randomNumberList(10));
    }
}
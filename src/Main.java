import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("¬ведите выражение");
        String userInput = scanner.nextLine();
        System.out.println(calc(userInput));
    }

    public static String calc(String input){
        String result = null;

        char operator = ' ';
        if (input.contains("+")){
            operator = '+';
        } else if (input.contains("-")) {
            operator = '-';
        } else if (input.contains("*")) {
            operator = '*';
        } else if (input.contains("/")) {
            operator = '/';
        }

        String[] str = input.replaceAll("\\s+","").toUpperCase().split("[+-/*]");
        try {
            checkSigns(input);
            String s1 = str[0];
            String s2 = str[1];
            if (isRoman(s1)&isRoman(s2)){
                int num1 = romanToArabic(s1);
                int num2 = romanToArabic(s2);
                checkNumbers(num1); checkNumbers(num2);
                result = arabicToRoman(arithmetic(num1,num2,operator));
            } else {
                int num1 = Integer.parseInt(s1);
                int num2 = Integer.parseInt(s2);
                checkNumbers(num1); checkNumbers(num2);
                result = String.valueOf(arithmetic(num1,num2,operator));
            }
        }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
            System.out.println("Incorrect input");
            System.exit(0);
        }
        return result;
    }

    static void checkNumbers(int num) {
        if (num > 10 || num < 1)
            throw new IllegalArgumentException();
    }

    static void checkSigns(String s){
        long Count = 0;
        Count += s.chars().filter(ch -> ch == '+').count();
        Count += s.chars().filter(ch -> ch == '-').count();
        Count += s.chars().filter(ch -> ch == '*').count();
        Count += s.chars().filter(ch -> ch == '/').count();
        if (Count>1){
            throw new IllegalArgumentException();
        }
    }
    static boolean isRoman(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public static int arithmetic (int num1, int num2, char op) {
        return switch (op) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            default -> throw new IllegalArgumentException();
        };
    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException();
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
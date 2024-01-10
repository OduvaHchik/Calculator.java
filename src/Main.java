import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите математическое выражение (например, 1 + 2):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new Exception("Неверный формат математического выражения.");
        }

        int num1 = RomanConverter.isRoman(tokens[0]) ? RomanConverter.romanToArabic(tokens[0]) : Integer.parseInt(tokens[0]);
        int num2 = RomanConverter.isRoman(tokens[2]) ? RomanConverter.romanToArabic(tokens[2]) : Integer.parseInt(tokens[2]);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно.");
        }

        String operator = tokens[1];
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль.");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неподдерживаемая арифметическая операция: " + operator);
        }

        return RomanConverter.isRoman(tokens[0]) ? RomanConverter.arabicToRoman(result) : String.valueOf(result);
    }
}

class RomanConverter {
    private static final Map<Character, Integer> romanNumerals = new HashMap<>();

    static {
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);
    }

    public static boolean isRoman(String input) {
        return input.matches("[IVXLCDM]+");
    }

    public static int romanToArabic(String input) throws Exception {
        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int currentValue = romanNumerals.get(input.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    public static String arabicToRoman(int number) throws Exception {
        if (number <= 0) {
            throw new Exception("Римские числа не могут быть неположительными или равными нулю.");
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : romanNumerals.entrySet()) {
            char romanDigit = entry.getKey();
            int value = entry.getValue();

            while (number >= value) {
                result.append(romanDigit);
                number -= value;
            }
        }

        return result.toString();
    }
}

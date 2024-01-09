import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Map<String, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put("I", 1);
        ROMAN_NUMERALS.put("II", 2);
        ROMAN_NUMERALS.put("III", 3);
        ROMAN_NUMERALS.put("IV", 4);
        ROMAN_NUMERALS.put("V", 5);
        ROMAN_NUMERALS.put("VI", 6);
        ROMAN_NUMERALS.put("VII", 7);
        ROMAN_NUMERALS.put("VIII", 8);
        ROMAN_NUMERALS.put("IX", 9);
        ROMAN_NUMERALS.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода. Используйте пример: 2 + 3");
        }

        try {
            int operand1 = parseOperand(tokens[0]);
            int operand2 = parseOperand(tokens[2]);
            String operator = tokens[1];

            int result;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        throw new ArithmeticException("Деление на ноль невозможно");
                    }
                    result = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая арифметическая операция");
            }

            return isRoman(input) ? toRoman(result) : String.valueOf(result);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректные числовые значения");
        }
    }

    private static int parseOperand(String operand) {
        if (isRoman(operand)) {
            return ROMAN_NUMERALS.get(operand);
        } else {
            int value = Integer.parseInt(operand);
            if (value < 1 || value > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
            }
            return value;
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("^[IVXLC]+$");
    }

    private static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Результат с римскими числами должен быть от 1 до 3999");
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
            while (number >= entry.getValue()) {
                result.append(entry.getKey());
                number -= entry.getValue();
            }
        }

        return result.toString();
    }
}

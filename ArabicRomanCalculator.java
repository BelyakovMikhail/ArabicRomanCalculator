import java.util.Locale;
import java.util.Scanner;
public class ArabicRomanCalculator {
    static int num1;
    static int num2;


    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение:");
        Scanner scan = new Scanner(System.in);
        String expression = scan.nextLine().toUpperCase();
        boolean hasDigit = expression.matches(".*\\d+.*");
        char[] array = expression.toCharArray();
        int count = 0;
        int count1=0;
        int count2=0;
        for (int i=0; i<array.length;i++){
            if (Character.isDigit(array[i])){
                count++;
            }
            if (Character.isLetter(array[i])){
                count1++;
            }
            if (!Character.isLetterOrDigit(array[i])){
                count2++;
            }
        }
        if (count>0 && count1>0) {
            throw new Exception("Ошибка! Используются одновременно разные системы счисления");
        }
        if (count2>1) throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        if ((count2==0 && count1 ==0) || (count2==0 && count==0)) throw new Exception("т.к. строка не является математической операцией");
        char operationSymbol = ' ';
        for (int i = 0; i < array.length; i++) { //определяем какую операцию калькулятор будет выполнять

            if (array[i] == '+') {
                operationSymbol = '+';
            } else if (array[i] == '-') {
                operationSymbol = '-';
            } else if (array[i] == '*') {
                operationSymbol = '*';
            } else if (array[i] == '/') {
                operationSymbol = '/';
            }
        }

        if (hasDigit) {
            num1 = arabicFirstNumber(expression, operationSymbol);
            num2 = arabicSecondNumber(expression, operationSymbol);
            int result = calculation(num1, num2, operationSymbol);
            System.out.println(result);
        }
        if (!hasDigit){
            String romanNum1 = expression.substring(0, expression.indexOf(operationSymbol));
            if (!(romanNum1.equals("I") || romanNum1.equals("II")||romanNum1.equals("III")||romanNum1.equals("IV")||romanNum1.equals("V")||romanNum1.equals("VI")||
                    romanNum1.equals("VII")||romanNum1.equals("VIII")||romanNum1.equals("IX")||romanNum1.equals("X"))) throw new Exception ("Ошибка! Введите число в интервале [I;X]");
            String romanNum2 = expression.substring(expression.indexOf(operationSymbol) + 1, expression.length());
            if (!(romanNum2.equals("I") || romanNum2.equals("II")||romanNum2.equals("III")||romanNum2.equals("IV")||romanNum2.equals("V")||romanNum2.equals("VI")||
                    romanNum2.equals("VII")||romanNum2.equals("VIII")||romanNum2.equals("IX")||romanNum2.equals("X"))) throw new Exception ("Ошибка! Введите число в интервале [I;X]");
            num1 = RomanNumbers.toInt(romanNum1);
            num2 = RomanNumbers.toInt(romanNum2);
            String result = romanCalculation(num1, num2, operationSymbol);
            System.out.println(result);

        }
    }
    public static int arabicFirstNumber (String s, char c) throws Exception{

        String line = s.trim();
        num1 = Integer.parseInt(line.substring(0, line.indexOf(c)));
        if (num1 < 1 || num1 >10) throw new Exception("Ошибка! Введите число в интервале [1;10]");
        return num1;
    }
    public static int arabicSecondNumber (String s, char c) throws Exception{
        String line = s.trim();
        num2 = Integer.parseInt(line.substring(line.indexOf(c)+1,line.length()));
        if (num2 < 1 || num2 >10) throw new Exception ("Ошибка! Введите число в интервале [1;10]");
        return num2;
    }
    public static int calculation (int num1, int num2, char operationSymbol) { // метод, который совершает арифметические операции и возвращает результат вычисления
        int result = 0;
        switch (operationSymbol) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                try {
                    result = num1 / num2;
                } catch (Exception e) {
                    System.out.println("Exception : " + e);
                    System.out.println("Делить на ноль нельзя");
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Неверный знак операции");
        }
        return result;
    }
    public static String romanCalculation (int num1, int num2, char operationSymbol) { // метод, который совершает арифметические операции и возвращает результат вычисления
        int result = 0;
        String finish = "";
        switch (operationSymbol) {
            case '+':
                result = num1 + num2;
                finish = RomanNumbers.convert(result);
                break;
            case '-':
                try {
                    result = num1 - num2;
                    finish = RomanNumbers.convert(result);
                } catch (Exception e){
                    System.out.println("В римской системе нет отрицательныйх чисел");
                }
                break;
            case '*':
                result = num1 * num2;
                finish = RomanNumbers.convert(result);
                break;
            case '/':
                try {
                    result = num1 / num2;
                    finish = RomanNumbers.convert(result);
                } catch (Exception e) {
                    System.out.println("Exception : " + e);
                    System.out.println("Делить на ноль нельзя");
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Неверный знак операции");
        }
        return finish;
    }
}

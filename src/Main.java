import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        try {
            System.out.println(calc(line));
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();

    }

    public static String calc(String input){
        int value1 = 0;
        int value2 = 0;
        boolean isLatin = false;
        String[] value = input.split(" ");
        if (value.length != 3) {
            throw new RuntimeException("не верный формат");
        }
        if (value[0].toUpperCase().contains("I") || value[0].toUpperCase().contains("V") || value[0].toUpperCase().contains("X")){
            try {
                value1 = latinToArabic(value[0]);
                value2 = latinToArabic(value[2]);
                isLatin = true;
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("не верный формат цифр");
            }
        } else {
            try {
                value1 = Integer.parseInt(value[0]);
                value2 = Integer.parseInt(value[2]);
                if ( value1 > 10 || value2 > 10) {
                    throw new RuntimeException("числа больше 10");
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("не верный формат цифр");
            }
        }


        switch (value[1]){
            case "+" : if (isLatin){
                return arabicToLatin(value1 + value2);
            } else {
                return String.valueOf(value1 + value2);
            }
            case "-" : if (isLatin){
                return arabicToLatin(value1 - value2);
            } else {
                return String.valueOf(value1 - value2);
            }
            case "*" : if (isLatin){
                return arabicToLatin(value1 * value2);
            } else {
                return String.valueOf(value1 * value2);
            }
            case "/" : if (isLatin){
                return arabicToLatin(value1 / value2);
            } else {
                return String.valueOf(value1 / value2);
            }
            default: throw new RuntimeException("формат математической операции не удовлетворяет заданию");
        }
    }

    public static int latinToArabic (String number) {
        int result = 0;
        char [] symbls = number.toUpperCase().toCharArray();
        for (int i = 0; i < symbls.length; i++) {
            switch (symbls[i]) {
                case 'M':
                    checkFiveNumbers(symbls, i);
                    if ((i + 1) < symbls.length && symbls[i] != 'M'
                        && symbls[i] != 'D' && symbls[i] != 'C' && symbls[i] != 'L'
                        && symbls[i] != 'X' && symbls[i] != 'V' && symbls[i] != 'I'){
                        throw new RuntimeException("такое число не существует");
                    }
                    result += 1000;
                    break;
                case 'D':
                    if ((i + 1) < symbls.length && symbls[i] != 'C' && symbls[i] != 'L'
                            && symbls[i] != 'X' && symbls[i] != 'V' && symbls[i] != 'I'){
                        throw new RuntimeException("такое число не существует");
                    }
                    result += 500;
                    break;
                case 'C':
                    checkFiveNumbers(symbls, i);
                    if ((i + 1) < symbls.length) {
                        if (symbls[i] != 'C' && symbls[i] != 'L' && symbls[i] != 'X'
                                && symbls[i] != 'V' && symbls[i] != 'I'){
                            throw new RuntimeException("такое число не существует");
                        }
                        if ('D' == symbls[i + 1] || 'M' == symbls[i + 1]){
                            result -= 100;
                        } else {
                            result += 100;
                        }
                    } else {
                        result += 100;
                    }
                    break;
                case 'L':
                    if ((i + 1) < symbls.length && symbls[i] != 'X' && symbls[i] != 'V' && symbls[i] != 'I'){
                        throw new RuntimeException("такое число не существует");
                    }
                    result += 50;
                    break;
                case 'X':
                    checkFiveNumbers(symbls, i);
                    if ((i + 1) < symbls.length){
                        if (symbls[i] != 'X' && symbls[i] != 'V' && symbls[i] != 'I'){
                            throw new RuntimeException("такое число не существует");
                        }
                        if ('C' == symbls[i + 1] || 'L' == symbls[i + 1]) {
                        result -= 10;
                    } else {
                            result += 10;
                        }
                }
                     else {
                        result += 10;
                    }
                    break;
                case 'V':
                    if ((i + 1) < symbls.length && symbls[i + 1] != 'I'){
                        throw new RuntimeException("такое число не существует");
                    }
                    result += 5;
                    break;
                case 'I':
                    checkFiveNumbers(symbls, i);
                    if ((i + 1) < symbls.length) {
                        if (symbls[i] != 'I'){
                            throw new RuntimeException("такое число не существует");
                        }
                        if ('X' == symbls[i + 1] || 'V' == symbls[i + 1]){
                            result -= 1;
                        } else {
                            result += 1;
                        }
                    } else {
                        result += 1;
                    }
                    break;
                default: throw new RuntimeException("такое число не существует");
            }
        }

        return result;
    }

    private static void checkFiveNumbers (char [] symbls , int i){
        if ((i + 4) < symbls.length &&
                symbls[i] == symbls[i + 1] &&
                symbls[i + 1] == symbls[i + 2] &&
                symbls[i + 2] == symbls[i + 3] &&
                symbls[i + 3] == symbls[i + 4]) {
            throw new RuntimeException("такое число не существует");
        }
    }
    public static String arabicToLatin (int number) {
        if (number <= 0){
            throw new RuntimeException("такое число не существует");
        }
       StringBuilder builder = new StringBuilder();
       while (number >= 1000){
           builder.append("M");
           number -= 1000;
       }
       if (number / 100 == 9){
           builder.append("CM");
           number -= 900;
       }
       if (number / 100 >= 5){
           builder.append("D");
           number -= 500;
       }
       if (number / 100 == 4){
           builder.append("CD");
           number -= 400;
       }
        while (number >= 100){
            builder.append("C");
            number -= 100;
        }
        if (number / 10 == 9){
            builder.append("XC");
            number -= 90;
        }
        if (number / 10 >= 5){
            builder.append("L");
            number -= 50;
        }
        if (number / 10 == 4){
            builder.append("XL");
            number -= 40;
        }
        while (number >= 10){
            builder.append("X");
            number -= 10;
        }
        if (number == 9){
            builder.append("IX");
            number -= 9;
        }
        if (number >= 5){
            builder.append("V");
            number -= 5;
        }
        if (number == 4){
            builder.append("IV");
            number -= 4;
        }
        while (number > 0){
            builder.append("I");
            --number;
        }
        return builder.toString();
    }
}

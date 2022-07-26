import java.util.Scanner;

enum  Op {MINUS, PLUS,MULTIPLY,DIVIDE} //not used actually

class Roman{
    int value(char r)
    {
        if ((r == 'I') || (r == 'i')) return 1;
        if ((r == 'V') || (r == 'v')) return 5;
        if ((r == 'X') || (r == 'x')) return 10;
        if ((r == 'L') || (r == 'l')) return 50;
        if ((r == 'C') || (r == 'c')) return 100;
        if ((r == 'D') || (r == 'd')) return 500;
        if ((r == 'M') || (r == 'm')) return 1000;
        return -1;
    }

    int romantodec(String s) throws Exception {
        int total = 0;
        for (int i=0; i<s.length(); i++) {
            int s1 = value(s.charAt(i));
            if (s1 == -1){
                throw new Exception("Transform error");
            }
            if (i+1 <s.length()) {
                int s2 = value(s.charAt(i+1));
                if (s1 >= s2) {
                    total = total + s1;
                } else {
                    total = total - s1;
                }
            } else {
                total = total + s1;
            }
        }
        return total;
    }

    String dectoroman(int num){
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        if(num == 0){ return "0"; }

        for(int i=0;i<values.length;i++) {
            while(num >= values[i]) {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }

        return roman.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Test calculator");
        System.out.println("Type mathematical expression or \"exit\" to quit");

        for(;;) {
            String inputString = myObj.nextLine();
            if (inputString.contains("exit")) {
                System.out.println("EXIT PROGRAM");
                break;
            }
            try {
                System.out.println(calc(inputString));
            } catch (Exception e) {
                e.printStackTrace();
                break; // comment to use after exceptions
            }
        }
    }

    public static String calc(String input) throws Exception {
        int operandA;
        int operandB;
        String resultString;

        //input with spaces only
        String[] arguments = input.split(" ");
        if(arguments.length != 3){
            throw new Exception("Format error");
        }

        Roman roman = new Roman();
        boolean romanSystem = false;
        try {
            operandA = Integer.parseInt(arguments[0]);
            operandB = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            operandA = roman.romantodec(arguments[0]);
            operandB = roman.romantodec(arguments[2]);
            romanSystem = true;
        }

        int result = switch (arguments[1]) {
            case "+" -> operandA + operandB;
            case "-" -> operandA - operandB;
            case "*" -> operandA * operandB;
            case "/" -> operandA / operandB;
            default -> throw new Exception("Unknown operation");
        };

        if(romanSystem){
            if(result < 0){
                throw new Exception("No negative numbers in Roman system");
            }
            resultString = roman.dectoroman(result);
        } else {
            resultString = String.valueOf(result);
        }

        return resultString;
    }


}
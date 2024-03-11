import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BigNumArithmetic{

    private LStack obj = new LStack();
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java BigNumArithmetic <input-file>");
            return;
        }

        String inputFile = args[0];
        BigNumArithmetic bigNumArithmetic = new BigNumArithmetic();
        bigNumArithmetic.processInputFile(inputFile);
    }

    public void processInputFile(String inputFile) {
        String result = "";
        String equation = "";
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                obj.clear();
                String line = scanner.nextLine().trim(); // Trim to remove leading/trailing whitespace
                equation = line.replaceAll("\\s+", " ") + " = ";
                String[] tokens = line.split("\\s+");
                String token = null;
                for (int i = 0; i < tokens.length; i++) {
                    token = tokens[i].trim();
                    if (token.length() == 0) {
                        continue;
                    }
                    if (!token.equals("+") && !token.equals("-") && !token.equals("*")) {
                        obj.push(token);
                    } else {
                        if (obj.length() < 2) {
                            System.out.println("Not enough values");
                        }
                        String operand1 = (String) obj.pop();
                        String operand2 = (String) obj.pop();
                        String operation = "";
                        if (token.equals("+")) {
                            operation = token;
                            result = performAddition(operand1, operand2);
                        } else if (token.equals("-")) {
                            result = performSubtraction(operand1, operand2);
                        } else if (token.equals("*")) {
                            result = performMultiplication(operand1, operand2);
                        }
                        //System.out.println("Result " + result);

                        obj.push(result);
                    }
                }
                if (!(token.length() == 0)) {
                    if(!(obj.length() == 1))
                    {
                        System.out.println(equation);
                    }else if(obj.length() == 1){
                        equation += obj.pop();
                        System.out.println(equation);
                    }
                }
            }

           // System.out.println(result);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }
    }
    public void ArrayToString(String[] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            System.out.print(array[i]);
        }
    }
    public LList stringToLL(String[] array) {
        LList list = new LList();
        for (int i = 0; i < array.length; i++) {
            String token = array[i];
            for (int j = 0; j < token.length(); j++) {
                char digitChar = token.charAt(j);
                int digit = Character.getNumericValue(digitChar);
                list.append(digit);
            }
        }
        return list;
    }

    public LList stringToLL(String number) {
        LList list = new LList();
        int index = 0;
        for(int n = 0; n < number.length(); n++)
        {
            if(number.charAt(n) != '0'){
                index = n;
                break;
            }
            if(n == number.length() -1){
                index = number.length() -1;
            }
        }
        number = number.substring(index);
        for (int i = number.length() -1; i >= 0; i--) {
            char digitChar = number.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            list.append(digit);
        }
        if(list.isEmpty()){
            list.append(0);
        }
        return list;
    }
    public String LLtoString(LList list){
        StringBuilder s = new StringBuilder();
        list.moveToStart();
        while(!list.isAtEnd()){
            try {
                s.append(list.getValue());
                list.next();
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }
        }
        return s.toString();
    }
    public String performAddition(String operand1, String operand2) {
        // Make list1 and list2 of equal length by padding with leading zeros if necessary
        LList list1 = stringToLL(operand1);
        LList list2 = stringToLL(operand2);
        int maxLength = Math.max(list1.length(), list2.length());
        while (list1.length() < maxLength) {
            list1.append(0); // Pad list1 with leading zeros
        }
        while (list2.length() < maxLength) {
            list2.append(0); // Pad list2 with leading zeros
        }

        // Initialize variables for addition
        StringBuilder sum = new StringBuilder();
        int carry = 0;

        // Traverse both operands from right to left and perform addition
        for (int i = 0; i < maxLength; i++) {
            int digit1 = (int) list1.getValue();
            int digit2 = (int) list2.getValue();

            int total = digit1 + digit2 + carry;
            int resultDigit = total % 10;
            carry = total / 10;

            // Prepend the resultDigit to the sum
            sum.insert(0, resultDigit);

            // Move to the previous nodes in both lists
            list1.next();
            list2.next();
        }

        // Add any remaining carry
        if (carry > 0) {
            sum.insert(0, carry);
        }



        // Return the sum as a string
        return sum.toString();
    }


    public String performSubtraction(String operand1, String operand2) {
        LList list1 = stringToLL(operand1);
        LList list2 = stringToLL(operand2);

        // Make list1 and list2 of equal length by padding with leading zeros if necessary
        int maxLength = Math.max(list1.length(), list2.length());
       return null;

    }
    public String performMultiplication(String operand1, String operand2) {
        LList list2 = stringToLL(operand1); // Second operand
        LList list1 = stringToLL(operand2); // First operand
        int max1 = list1.getSize();
        int max2 = list2.getSize();
        int maxLength = Math.max(list1.length(), list2.length());
        // Initialize variables for addition
        String sum = String.valueOf(new StringBuilder());
        String totalFinal = "";
        String total = "";
        int digit1 = 0;
        int digit2 = 0;
        String multiplier1 = "";
        String multiplier2 = "";
        for(int i = 0; i < max1; i++){
            digit1 = (int) list1.getValue();
            multiplier2 = "";
            list2.moveToStart();
            for(int j =0; j < max2; j++){
                digit2 = (int) list2.getValue();
                total = performAddition(total, String.valueOf(digit1 * digit2) + multiplier1 + multiplier2);
                list2.next();
                multiplier2 += "0";
            }
            list1.next();
            multiplier1 += "0";
        }

        return total;
    }
}


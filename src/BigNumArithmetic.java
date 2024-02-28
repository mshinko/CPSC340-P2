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
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Trim to remove leading/trailing whitespace
                String[] tokens = line.split("\\s+");
                for (int i = 0; i < tokens.length; i++) {
                    String token = tokens[i].trim();
                    if (!token.equals("+") && !token.equals("-") && !token.equals("*")) {
                        obj.push(token);
                    } else {
                        if (obj.length() < 2) {
                            System.out.println("Not enough values");
                        }
                        String operand1 = (String) obj.pop();
                        String operand2 = (String) obj.pop();
                        if(token.equals("+"))
                        {
                            result = performAddition(operand1, operand2);
                        } else if(token.equals("-"))
                        {
                            result = performSubtraction(operand1, operand2);
                        } else if(token.equals("*"))
                        {
                            result = performMultiplication(operand1, operand2);
                        }

                        //System.out.println("Result " + result);
                        obj.push(result);
                    }
                }
                System.out.println(result);
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
        boolean leadingZero = true;
        for (int i = 0; i < number.length(); i++) {
            char digitChar = number.charAt(i);
            if(leadingZero && digitChar == '0')
            {
                continue;
            }
            int digit = Character.getNumericValue(digitChar);
            list.append(digit);
            leadingZero = false;
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
        // Remove leading zeros from operands
        LList list1 = stringToLL(operand1);
        LList list2 = stringToLL(operand2);
        operand1 = operand1.replaceFirst("^0+(?!$)", "");
        operand2 = operand2.replaceFirst("^0+(?!$)", "");

        // Make operand1 and operand2 of equal length by padding with leading zeros if necessary
        int maxLength = Math.max(operand1.length(), operand2.length());
        operand1 = String.format("%" + maxLength + "s", operand1).replace(' ', '0');
        operand2 = String.format("%" + maxLength + "s", operand2).replace(' ', '0');

        // Initialize variables for addition
        StringBuilder sum = new StringBuilder();
        int carry = 0;

        // Traverse both operands from right to left and perform addition
        for (int i = maxLength - 1; i >= 0; i--) {
            int digit1 = operand1.charAt(i) - '0';
            int digit2 = operand2.charAt(i) - '0';

            int total = digit1 + digit2 + carry;
            int resultDigit = total % 10;
            carry = total / 10;

            // Prepend the resultDigit to the sum
            sum.insert(0, resultDigit);
        }

        // Add any remaining carry
        if (carry > 0) {
            sum.insert(0, carry);
        }

        // Return the sum as a string
        return sum.toString();
    }

    public String performSubtraction(String operand1, String operand2){

        return null;
    }
    public String performMultiplication(String operand1, String operand2){
        return null;
    }
}


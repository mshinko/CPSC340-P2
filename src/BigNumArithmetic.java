
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.NoSuchElementException;
        import java.util.Scanner;

public class BigNumArithmetic {

    private LStack obj = new LStack();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java BigNumArithmetic <input-file>");
            return;
        }

        String inputFile = args[0];


        // Process input file
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            BigNumArithmetic bigNumArithmetic = new BigNumArithmetic();
            // While loop to read the file when there are lines present
            while (scanner.hasNextLine()) {
                // Clear the stack
                bigNumArithmetic.obj.clear();
                // Each line as a string
                String line = scanner.nextLine().trim(); // Trim to remove leading/trailing whitespace
                if (line.isEmpty()) {
                    continue;
                }
                String equation = line.replaceAll("\\s+", " ") + " = "; // Replace the line
                // Split the line into tokens
                String[] tokens = line.split("\\s+");
                String token;
                int numOperators = 0;
                int numOperands = 0;
                // Boolean for if there are operands
                boolean hasOperands = false;
                // For loop to sort through the tokens
                for (int i = 0; i < tokens.length; i++) {
                    token = tokens[i].trim();
                    if (token.length() == 0) {
                        continue;
                    }
                    // If the token is a number, push it, increase the operands count, and set the hasOperands flag to true
                    if (!token.equals("+") && !token.equals("-") && !token.equals("*")) {
                        bigNumArithmetic.obj.push(token);
                        numOperands++;
                        hasOperands = true;
                    } else {
                        // Otherwise, it's an operator
                        numOperators++;
                        // If there are no operands or more operators than operands, break
                        if (!hasOperands || numOperators >= numOperands) {
                            break;
                        }
                        // If the stack has fewer than 2 elements, print the equation and continue
                        if (bigNumArithmetic.obj.length() < 2) {
                            System.out.println(equation);
                            continue;
                        } else {
                            // Pop two operands from the stack
                            String operand1 = (String) bigNumArithmetic.obj.pop();
                            String operand2 = (String) bigNumArithmetic.obj.pop();
                            String result;
                            // Perform the corresponding operation
                            if (token.equals("+")) {
                                result = bigNumArithmetic.performAddition(operand1, operand2);
                            } else if (token.equals("-")) {
                                result = bigNumArithmetic.performSubtraction(operand1, operand2);
                            } else if (token.equals("*")) {
                                result = bigNumArithmetic.performMultiplication(operand1, operand2);
                            } else {
                                // This case should never happen, but if it does, print the equation and continue
                                System.out.println(equation);
                                continue;
                            }
                            // Push the result of the performed operation
                            bigNumArithmetic.obj.push(result);
                        }
                    }
                }
                // If the stack length is 1 and the number of operands is one more than the number of operators, print the equation and result
                if (bigNumArithmetic.obj.length() == 1 && numOperands == numOperators + 1) {
                    equation += bigNumArithmetic.obj.pop();
                    System.out.println(equation);
                } else {
                    // Otherwise, just print the equation
                    System.out.println(equation);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }
    }

    public LList stringToLL(String number) {
        //Create a list of LList
        LList list = new LList();
        // set an index
        int index = 0;
        //Loop iterating over every value of the string number
        for (int n = 0; n < number.length(); n++) {
            // is current character equal to 0
            if (number.charAt(n) != '0') {
                //if it is not 0 it updates the index to the current index which is n
                index = n;
                break;
            }
            //if all characters in the string are 0 it sets it too the last index of the string
            if (n == number.length() - 1) {
                index = number.length() - 1;
            }
        }
        //Removes leading zeros
        number = number.substring(index);
        //This loop iterates over each character of the updated number in reverse order
        for (int i = number.length() - 1; i >= 0; i--) {
            //character at index i
            char digitChar = number.charAt(i);
            //converts the char to the numeric representation
            int digit = Character.getNumericValue(digitChar);
            //adds the digit to the linked list
            list.append(digit);
        }
        //if list empty just put a 0
        if (list.isEmpty()) {
            list.append(0);
        }
        //send back the list
        return list;
    }

    public String LLtoString(LList list) {
        //Creates a stringbuilder
        StringBuilder s = new StringBuilder();
        //Starts at the beginning of the list
        list.moveToStart();
        //While there is more to look at
        while (!list.isAtEnd()) {
            try {
                //append the current value to the string
                s.append(list.getValue());
                list.next();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        //Return the string value
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
        LList list1;
        LList list2;
        operand1 = operand1.replaceFirst("^0+(?!$)", "");
        operand2 = operand2.replaceFirst("^0+(?!$)", "");
        if (operand1.length() > operand2.length()) {
            list1 = stringToLL(operand1);
            list2 = stringToLL(operand2);
        } else if (operand1.length() < operand2.length()) {
            list1 = stringToLL(operand2);
            list2 = stringToLL(operand1);
        } else {
            int comparison = operand1.compareTo(operand2);
            if (comparison == 0) {
                list1 = stringToLL(operand1);
                list2 = stringToLL(operand2);
            } else if (comparison > 0) {
                list1 = stringToLL(operand1);
                list2 = stringToLL(operand2);
            } else {
                list1 = stringToLL(operand2);
                list2 = stringToLL(operand1);
            }
        }
        int max1 = list1.length();
        int max2 = list2.length();
        int digit1 = 0;
        int digit2 = 0;
        int maxLength = Math.max(list1.length(), list2.length());
        StringBuilder difference = new StringBuilder();
        int borrow = 0;
        while (list1.length() < maxLength) {
            list1.append(0); // Pad list1 with leading zeros
        }
        while (list2.length() < maxLength) {
            list2.append(0); // Pad list2 with leading zeros
        }
        // Traverse both operands from right to left and perform subtraction
        for (int i = 0; i < maxLength; i++) {
            digit1 = (int) list1.getValue();
            digit2 = (int) list2.getValue();

            digit1 -= borrow;
            borrow = 0;
            // If digit1 is smaller than digit2, borrow from the next higher place value
            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            }
            // Subtract digit2 from digit1
            int resultDigit = digit1 - digit2;
            // Append the resultDigit to the difference
            difference.insert(0, resultDigit);

            // Move to the previous nodes in both lists
            list1.next();
            list2.next();
        }

        // Return the difference as a string
        return difference.toString().replaceFirst("^0+(?!$)", "");

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



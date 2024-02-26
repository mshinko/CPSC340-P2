import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BigNumArithmetic{
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
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Trim to remove leading/trailing whitespace
                String[] splitted = line.split("\\s+");
                AStack stack = new AStack();
                for(String split : splitted){
                    if(split.matches("\\d+")){
                        LList operand = stringToLL(split);
                        stack.push(operand);
                    } else if (split.equals("+")){
                        performAddition(stack);
                    }else {
                        System.out.println("Invalid " + split);
                        break;
                    }
                }
                if (stack.length() == 1)
                {
                    LList result = (LList) stack.pop();
                    System.out.println(LLtoString(result));
                }
            }
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
        for (int i = 0; i < number.length(); i++) {
            char digitChar = number.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            list.append(digit);
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
    public void performAddition(AStack stack){
        if(stack.length() < 2){
            System.out.println("Not enough values");
            return;
        }
        LList operand2 = (LList) stack.pop();
        LList operand1 = (LList) stack.pop();

        LList result = new LList();
        int carry = 0;

        operand1.moveToStart();
        operand2.moveToStart();

        while (!operand1.isAtEnd() || !operand2.isAtEnd() || carry > 0)
        {
            int digit1 = operand1.isAtEnd() ? 0 : (int) operand1.getValue();
            int digit2 = operand2.isAtEnd() ? 0 : (int) operand2.getValue();

            int sum = digit1 + digit2 + carry;

            result.append(sum % 10);
            carry = sum/10;

            operand1.next();
            operand2.next();
        }
        stack.push(result);
    }
}


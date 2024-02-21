import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BigNumArithmetic {
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
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }
    }

    private void processExpression(String expression) {
    }
}


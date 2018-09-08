import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your expression under this message. Enter *exit* if you want to exit.");

        while (true) {
            try {
                String inputInfix = in.nextLine();

                if (inputInfix.equals("exit") || inputInfix.equals("Exit") || inputInfix.equals("EXIT")) {
                    break;
                }

                // Convert from Infix to Postfix in order to simplify solving process
                String input = InfixToPostfix.convertToPostfix(inputInfix);
                System.out.println("Conversion to postfix is: " + input);
                // Parse prefix string
                Expression expression = Expression.parsePostOrder(input);
                // Show what we've parsed
                System.out.println("Your expression is: " + expression.toString());
                // Solve parsed string
                System.out.println("Answer is: " + expression.evaluate());
            } catch (InvalidExpressionException e) {
                System.out.println("Invalid expression: " + e.getMessage());
            }

            System.out.println();
            System.out.println("Enter your expression under this message. Enter *exit* if you want to exit.");
        }
    }
}

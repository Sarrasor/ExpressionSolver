import java.util.Stack;
import java.util.StringTokenizer;

class InfixToPostfix
{
    static String convertToPostfix(String infix)
    {
        Stack operatorStack = new Stack();
        char c;

        // Allowed operands
        StringTokenizer parser = new StringTokenizer(infix,"+-*/()=>< ",true);

        StringBuilder postfix = new StringBuilder(infix.length());

        while (parser.hasMoreTokens())
        {
            String token = parser.nextToken();
            c = token.charAt(0);
            if ((token.length() == 1) && isOperator(c))
            {
                while (!operatorStack.empty() && !Precedence(((String)operatorStack.peek()).charAt(0), c))
                {
                    postfix.append(" ").append((String) operatorStack.pop());
                }

                if (c==')')
                {
                    String operator = (String)operatorStack.pop();
                    while (operator.charAt(0)!='(')
                    {
                        postfix.append(" ").append(operator);
                        operator = (String)operatorStack.pop();
                    }
                }
                else {
                    operatorStack.push(token);
                }
            }
            else if ( (token.length() == 1) && c == ' ')
            {
                // Do nothing if there is a space
                // You could throw an Error, but why?
            }
            else {
                postfix.append(" ").append(token);
            }
        }

        while (!operatorStack.empty())
        {
            postfix.append(" ").append((String) operatorStack.pop());
        }

        return postfix.toString();
    }


    private static boolean isOperator(char c)
    {
        return c == '+'  ||  c == '-'  ||  c == '*'  ||  c == '='  ||  c == '>' ||  c == '<' || c=='(' || c==')';
    }


    private static boolean Precedence(char op1, char op2)
    {
        // op1 and op2 are assumed to be operator characters (+,-,*,=,>,<).

        switch (op1)
        {

            case '+':
            case '-':
                return !(op2=='+' || op2=='-');

            case '*':
                return op2=='^' || op2=='(';

            case '=':
                return op2=='=' || op2=='(';

            case '>':
                return op2=='>' || op2=='(';

            case '<':
                return op2=='<' || op2=='(';

            case '(': return true;

            default:
                return false;
        }

    }
}

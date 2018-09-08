import java.util.Stack;
import java.util.Scanner;

public abstract class Expression
{
    public abstract int evaluate();

    public String toPostOrder()
    {
        return this.toString();
    }

    public String toInOrder()
    {
        return this.toString();
    }

    static Expression parsePostOrder(String expression)
    {
        Scanner tokenizer = new Scanner(expression);
        Stack<Expression> stack = new Stack<>();

        while (tokenizer.hasNext())
        {
            if(tokenizer.hasNextInt())
            {
                stack.push(new Number(tokenizer.nextInt()));
            }
            else {
                String token = tokenizer.next();
                stack.push(Operator.fromSymbol(token, stack));
            }
        }

        if (stack.size() != 1)
        {
            throw new InvalidExpressionException("Not enough operators.");
        }

        return stack.pop();
    }
}

class Number extends Expression
{
    private int value;

    Number(int value)
    {
        this.value = value;
    }

    public int evaluate()
    {
        return this.value;
    }

    public String toString()
    {
        return  String.valueOf(this.value);
    }
}


abstract class Operator extends Expression
{
    String symbol;

    Operator(String symbol)
    {
        this.symbol = symbol;
    }

    static Operator fromSymbol(String symbol, Stack<Expression> parseStack)
    {
        switch (symbol)
        {
            case "+":
                return new AddOperator(parseStack);
            case "-":
                return new SubtractOperator(parseStack);
            case "*":
                return new MultiplyOperator(parseStack);
            case "=":
                return new EqualsOperator(parseStack);
            case  ">":
                return new MoreOperator(parseStack);
            case "<":
                return new LessOperator(parseStack);
            default:
                throw new InvalidExpressionException("Invalid operator: " + symbol);
        }
    }
}

abstract class BinaryOperator extends Operator
{
    Expression left;
    Expression right;

    BinaryOperator(String symbol, Stack<Expression> parseStack)
    {
        super(symbol);

        if (parseStack.size() < 2)
        {
            throw new InvalidExpressionException("Not enough parameters for binary operator " + symbol);
        }

        this.right = parseStack.pop();
        this.left = parseStack.pop();
    }

    public String toString() {
        return this.toInOrder();
    }

    public String toInOrder()
    {
        return this.left.toInOrder() + " " + this.symbol + " " + this.right.toInOrder();
    }

    public String toPostOrder()
    {
        return this.left.toPostOrder() + " " + this.right.toPostOrder() + " " + this.symbol;
    }
}

class AddOperator extends BinaryOperator
{
    AddOperator(Stack<Expression> parseStack)
    {
        super("+", parseStack);
    }

    public int evaluate()
    {
        return this.left.evaluate() + this.right.evaluate();
    }
}

class SubtractOperator extends BinaryOperator
{
    SubtractOperator(Stack<Expression> parseStack)
    {
        super("-", parseStack);
    }

    public int evaluate()
    {
        return this.left.evaluate() - this.right.evaluate();
    }
}

class MultiplyOperator extends BinaryOperator
{
    MultiplyOperator(Stack<Expression> parseStack)
    {
        super("*", parseStack);
    }

    public int evaluate()
    {
        return this.left.evaluate() * this.right.evaluate();
    }
}

class EqualsOperator extends BinaryOperator
{
    EqualsOperator(Stack<Expression> parseStack)
    {
        super("=", parseStack);
    }

    public int evaluate()
    {
        if (this.left.evaluate() == this.right.evaluate())
        {
            return 1;
        }
        return 0;
    }
}

class MoreOperator extends BinaryOperator
{
    MoreOperator(Stack<Expression> parseStack)
    {
        super(">", parseStack);
    }

    public int evaluate()
    {
        if (this.left.evaluate() > this.right.evaluate())
        {
            return 1;
        }
        return 0;
    }
}

class LessOperator extends BinaryOperator
{
    LessOperator(Stack<Expression> parseStack)
    {
        super("<", parseStack);
    }

    public int evaluate()
    {
        if (this.left.evaluate() < this.right.evaluate())
        {
            return 1;
        }
        return 0;
    }
}
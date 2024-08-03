import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        //creating int variables, all set to 0.

        int firstNumber = 0;
        int secondNumber = 0;
        int thirdNumber = 0;

        if (exp.numberOfChildren() <= 0) {
            //if statement checking if there's no children then it'll return the
            //value of the attribute in the tree
            firstNumber = Integer.parseInt(exp.attributeValue("value"));
        } else {
            //getting the first two numbers in the expression and putting them
            //into int variables.
            secondNumber = evaluate(exp.child(0));
            thirdNumber = evaluate(exp.child(1));
            //if statement saying if the label is equal to mathematical function,
            //then it'll be performed.
            if (exp.label().equals("plus")) {
                //adds one number to another then copies the sum to the
                //firstNumber variable. Order doesn't matter here because
                //it's addition.
                firstNumber = secondNumber + thirdNumber;
            } else if (exp.label().equals("minus")) {
                //subtracts one number from another then copies the difference
                //to the firstNumber variable.
                firstNumber = secondNumber - thirdNumber;
            } else if (exp.label().equals("times")) {
                //multiplies one number to another then copies the product to the
                //firstNumber variable. Order doesn't matter here because
                //it's multiplication.
                firstNumber = secondNumber * thirdNumber;
            } else {
                //divides one number from another then copies the quotient to the
                //firstNumber variable.
                firstNumber = secondNumber / thirdNumber;
            }
        }

        return firstNumber;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }
        in.close();
        out.close();
    }

}
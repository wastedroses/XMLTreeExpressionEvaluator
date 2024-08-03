import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        //creating nn variables, all set to 0.

        NaturalNumber firstNumber = new NaturalNumber2(0);
        NaturalNumber secondNumber = new NaturalNumber2(0);
        NaturalNumber thirdNumber = new NaturalNumber2(0);

        //if statement checking if there's no children then it'll return the
        //value of the attribute in the tree
        if (exp.numberOfChildren() <= 0) {
            firstNumber = new NaturalNumber2(exp.attributeValue("value"));
        } else {
            //getting the first two numbers in the expression and putting them
            //into nn variables.
            secondNumber.copyFrom(evaluate(exp.child(0)));
            thirdNumber.copyFrom(evaluate(exp.child(1)));
            //if statement saying if the label is equal to mathematical function,
            //then it'll be performed.
            if (exp.label().equals("plus")) {
                //adds one number to another then copies the sum to the
                //firstNumber variable. Order doesn't matter here because
                //it's addition.
                secondNumber.add(thirdNumber);
                firstNumber.copyFrom(secondNumber);
            } else if (exp.label().equals("minus")) {
                //subtracts one number from another then copies the difference
                //to the firstNumber variable.
                secondNumber.subtract(thirdNumber);
                firstNumber.copyFrom(secondNumber);
            } else if (exp.label().equals("times")) {
                //multiplies one number to another then copies the product to the
                //firstNumber variable. Order doesn't matter here because
                //it's multiplication.
                secondNumber.multiply(thirdNumber);
                firstNumber.copyFrom(secondNumber);
            } else {
                //if statement saying that if the NN is zero, or being
                //divided by zero, then the code won't work. This is also
                //included in the requires clause of the NN divide() function.
                //This will bring a fatalErrorToConsole message.
                if (thirdNumber.isZero()) {
                    String message = "Number cannot be zero. Try again.";
                    Reporter.fatalErrorToConsole(message);
                } else {
                    //divides one number from another then copies the quotient to the
                    //firstNumber variable.
                    firstNumber = secondNumber.divide(thirdNumber);
                    firstNumber.add(secondNumber);
                }
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
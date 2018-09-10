/**
 * Simplifies the equation given in structured formant in a JSON file
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.*;

/**
 * Below class creates each node of expression tree 
 */
class Node {
    String data; 
    Node left, right;

    Node(String data) {
        this.data = data;
        left = right = null;
    }
}

/**
 * Below class prints a string where 'x' is on LHS and rest operands are on RHS of 'equals'
 */
class ExpressionOfX {
    Stack<String> operands;
    Queue<String> operators;

    /** Set the values of stack of operands and queues of operators */
    public void setValues(Stack<String> operands, Queue<String> operators) {
        this.operands = (Stack<String>)operands.clone();
        this.operators = new LinkedList<>(operators);
    }

    /** Prints the complete expression */
    void printExpression(String variable) {
        // "result" stores our final expression
        String result = variable;
        // flag is used to remove data alternatively from stack of operands and queue of operators
        int flag = 0, bracketNeed = 0;
        int count = operators.size()-2;

        while(!operators.isEmpty() || !operands.isEmpty()) {
            if(flag==0) {
                if(!operators.isEmpty()) {
                    String str = operators.remove();
                    if(str.equals("+")) {
                        result += "-";
                        bracketNeed = 1;
                    }
                    else if(str.equals("-")) {
                        result += "+";
                        bracketNeed = 1;
                    }
                    else if(str.equals("*")) {
                        result += "/";
                        bracketNeed = 1;
                    }
                    else if(str.equals("/")) {
                        result += "*";
                        bracketNeed = 1;
                    }
                    else 
                        result += str;
                }
                flag = 1;
            }
            else if(flag==1) {
                if(!operands.isEmpty()) {
                    while(count-->0) {
                        result += "(";
                    }
                    String str = operands.pop();
                    if(bracketNeed == 1 && operands.size()!=1) 
                        result += str + ")";
                    else
                        result += str;
                }
                flag = 0;
            }

            // This condition is written because we don't want to print 'x' in last position, 
            if(operands.size()==1)
                break;
        }

        System.out.println(result); 
    }
}

/**
 * Below class computes the value of 'x' from the expression
 */
class ValueOfX {
    Stack<String> operands;
    Queue<String> operators;

    /** Set the values of stack of operands and queues of operators */
    public void setValues(Stack<String> operands, Queue<String> operators) {
        this.operands = (Stack<String>)operands.clone();
        this.operators = new LinkedList<>(operators);
    }

    /** Find the value of 'x' using certain stack and queue operations */
    public void findX() {
        operators.remove();
        String str = "";
        while(operands.size()!=2) {
            int op1 = Integer.parseInt(operands.pop());
            int op2 = Integer.parseInt(operands.pop());

            if(!operators.isEmpty()) {
                str = operators.remove();
                int res = 0;
                if(str.equals("+"))
                    res = op1 - op2;
                else if(str.equals("-"))
                    res = op1 + op2;
                else if(str.equals("*"))
                    res = op1 / op2;
                else if(str.equals("/"))
                    res = op1 * op2;
                operands.push(String.valueOf(res));
            }
        }

        // Prints the final value of 'x'
        System.out.println("Value of x = "+operands.pop());
    }
}

/**
 * Driver Class
 */
public class EquationSimplification {
    static int max, count;
    static int bracketNeed = 0;

    /** Parse the input JSON file */
    void parseJSONFile(Map lhs, Stack<String> operands, Queue<String> operators) {
        Iterator<Map.Entry> it = lhs.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = it.next();
            Object ob = (Object)pair.getValue();

            if(ob instanceof String || ob instanceof Long) {
                String val = String.valueOf(ob);
                if(val.equals("equal"))
                    operators.add("=");
                else if(val.equals("add"))
                    operators.add("+");
                else if(val.equals("multiply"))
                    operators.add("*");
                else if(val.equals("subtract"))
                    operators.add("-");
                else if(val.equals("divide"))
                    operators.add("/");
                else 
                    operands.push(val);
            }
            else {
                Map lhs1 = (Map)ob;
                parseJSONFile(lhs1, operands, operators);
            }
        }

        count = operators.size();
        max = count-2;

        return;
    }

    /** Finds if the string parsed is an operator or not */
    static boolean isOperator(String data) {
        if(data.equals("+") || data.equals("-") || data.equals("*") || data.equals("/") || data.equals("="))
            return true;
        return false;
    }

    /** Performs recursive InOrder Traversal on expression tree to print the correct equation */
    static void inOrderTraversal(Node node) {
        if(node == null)
            return;

        inOrderTraversal(node.left);

        while(max-->0)
            System.out.print("(");

        System.out.print(node.data);

        if(bracketNeed==1 && count>=2)
            System.out.print(")");

        if(isOperator(node.data)) {
            bracketNeed = 1;
            count--;
        }
        else 
            bracketNeed = 0;
        
        inOrderTraversal(node.right);
    }

    /** Main Driver Function */
    public static void main(String[] args) throws Exception{
        Stack<String> operands = new Stack<>();
        Queue<String> operators = new LinkedList<>();

        // Converts the JSON file into JSON object and then to map
        JSONParser parser = new JSONParser();
        JSONObject ob = (JSONObject)parser.parse(new FileReader("EquationData.json"));
        Map datMap = (Map)ob;

        // Creates driver class object and then call the parseJSONFile method to parse the JSON file
        EquationSimplification simplification = new EquationSimplification();
        simplification.parseJSONFile(datMap, operands, operators);

        // Creates object of ExpressionOfX class and then set the values for stack of operands and queue of operators
        ExpressionOfX expression = new ExpressionOfX();
        expression.setValues(operands, operators);

        // Creates object of ValueOfX class and then set the values for stack of operands and queue of operators
        ValueOfX valueOfX = new ValueOfX();
        valueOfX.setValues(operands, operators);

        Node root = null, temp = null;
        // flag is used to remove data alternatively from stack of operands and queue of operators
        int flag = 0;
        String str="";

        while(!operands.isEmpty() || !operators.isEmpty())  {
            if(flag==0) {
                if(!operators.isEmpty()) {
                    str = operators.remove();
                    if(root == null) {
                        root = new Node(str);
                        temp = root;
                    }
                    else {
                        Node child = new Node(str);
                        temp.left = child;
                        temp = temp.left;
                    }
                }
                flag = 1;
            }
            else {
                if(!operands.isEmpty())
                    str = operands.pop();
                Node child = new Node(str);
                if(temp.right == null) 
                    temp.right = child;
                else
                    temp.left = child;
                flag = 0;                   
            }
        }

        // Calls the method to print the expression tree in InOrder manner
        inOrderTraversal(root);
        System.out.println();

        // Calls the method to print the equation with 'x' on one side and other operands on other side
        expression.printExpression(str);

        // Calls the method to find the final value of 'x' and print it
        valueOfX.findX();
    }
}
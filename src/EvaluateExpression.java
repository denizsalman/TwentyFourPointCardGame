

import java.util.Stack;

public class EvaluateExpression {

	public static void main(String[] args) {
		
		String s = " ((2 + 3) * 3 )";
		
		System.out.println(evaulateExpression(s));
	}
	
	public static int evaulateExpression(String expression) {
		Stack<Integer> operandStack = new Stack<>();
		Stack<String> operatorStack = new Stack<>();
		
		expression = insertBlanks(expression);
		
		String[] tokens = expression.split(" ");
		
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (token.length() == 0) {
				continue;
			} else if (token.equals("(")) {
				operatorStack.push(token);
			} else if (token.equals("+") || token.equals("-")) {
				while (!operatorStack.isEmpty() &&
						(operatorStack.peek().equals("+") ||
						 operatorStack.peek().equals("-") ||
						 operatorStack.peek().equals("*") ||
						 operatorStack.peek().equals("/")
						 )) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.push(token);
			} else if (token.equals("*") || token.equals("/")) {
				operatorStack.push(token);
			} else if (token.equals(")")) {
				while (!operatorStack.peek().equals("(")) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.pop();
			} else {
				operandStack.push(Integer.parseInt(token));
			}
		}
		
		while (!operatorStack.isEmpty()) {
			processAnOperator(operandStack, operatorStack);
		}
		
		return operandStack.pop();
	}
	
	public static void processAnOperator(Stack<Integer> operandStack, 
			Stack<String> operatorStack) {
		String operator = operatorStack.pop();
		Integer operand1 = operandStack.pop();
		Integer operand2 = operandStack.pop();
		
		if (operator.equals("+")) {
			operandStack.push(operand1 + operand2);
		} else if (operator.equals("-")) {
			operandStack.push(operand1 - operand2);
		} else if (operator.equals("*")) {
			operandStack.push(operand1 * operand2);
		} else if (operator.equals("/")) {
			operandStack.push(operand1 / operand2);
		}
	}
	
	public static String insertBlanks(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '(' || s.charAt(i) == ')' ||
					s.charAt(i) == '+' || s.charAt(i) == '-' ||
					s.charAt(i) == '*' || s.charAt(i) == '/') {
				result += " " + s.charAt(i) + " ";
			} else if(s.charAt(i) != ' ') {
				result += s.charAt(i);
			}
		}
		return result.trim();
	}
}

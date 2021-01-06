

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TwentyFourPointPane extends BorderPane {
	private Label lbResult = new Label();
	private Button btShuffle = new Button("Shuffle");
	private TextField tfExpression = new TextField();
	private Button btVerify = new Button("Verify");
	private CardPane cardPane = new CardPane();
	private Stack<Integer> operandStack = new Stack<>();
	private Stack<Character> operatorStack = new Stack<>();
	
	
	public TwentyFourPointPane() {
		initialize();
	}

	private void initialize() {
		HBox hbShuffle = new HBox(10);
		hbShuffle.getChildren().addAll(lbResult, btShuffle);
		hbShuffle.setAlignment(Pos.TOP_RIGHT);
		
		HBox hbExpression = new HBox(10);
		hbExpression.getChildren().addAll(
				new Label("Enter an expression:"), tfExpression, btVerify);
		
		
		setTop(hbShuffle);
		setCenter(cardPane);
		setBottom(hbExpression);
		
		btShuffle.setOnAction(e -> cardPane.shuffle());
		btVerify.setOnAction(e -> verify());
	}
	
	private void verify() {
		cardPane.getCardNumberList();
		String expression = tfExpression.getText();
		expression = insertBlanks(expression);
		String[] tokens = expression.split(" ");
		if (isMatched(tokens)) {
			lbResult.setText("Correct");
		} else {
			lbResult.setText("The numbers in the expression don't"
					+ "\nmatch the numbers in the set");
		}
		int result = evaluateExpression(tokens);
		if (result != 24) {
			lbResult.setText("Incorrect result");
		}
		
	}
	
	private boolean isMatched(String[] tokens) {
		List<Integer> operandList = getOperands(tokens);
		if (operandList.size() != 4) {
			return false;
		} else {
			for (int i = 0; i < operandList.size(); i++) {
				int cardNumber = cardPane.getCardNumberList().get(i) % 13 == 0 ? 13 : cardPane.getCardNumberList().get(i) % 13;
				if (operandList.get(i) != cardNumber) {
					return false;
				}
			}
		}
		return true;
	}
	
	private int evaluateExpression(String[] tokens) {
		operandStack.clear();
		operatorStack.clear();
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
			if (tokens[i].isBlank()) {
				continue;
			} else if (tokens[i].equals("+") || tokens[i].equals("-")) {
				while (!operatorStack.isEmpty() && (operatorStack.peek() == '+'
						|| operatorStack.peek() == '-' || operatorStack.peek() == '*'
						|| operatorStack.peek() == '/')) {
					processOperator();
				}
				operatorStack.push(tokens[i].charAt(0));
			} else if (tokens[i].equals("*") || tokens[i].equals("/")) {
				operatorStack.push(tokens[i].charAt(0));
			} else if (!isOperator(tokens[i].charAt(0))){
				operandStack.push(Integer.parseInt(tokens[i]));
				if (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/')) {
					processOperator();
				}
			} else if (tokens[i].equals(")")) {
				while (operatorStack.peek() != '(') {
					processOperator();
				}
			}
			
		}
		
		while (!operatorStack.isEmpty()) {
			processOperator();
		}
		
		return operandStack.pop();
	}
	
	private void processOperator() {
		int b = operandStack.pop();
		int a = operandStack.pop();
		char op = operatorStack.pop();
		
		if (op == '+')
			operandStack.push(a + b);
		else if (op == '-')
			operandStack.push(a - b);
		else if (op == '*')
			operandStack.push(a * b);
		else if (op == '/')
			operandStack.push(a / b);
	}
	
	private List<Integer> getOperands(String[] tokens) {
		List<Integer> operandList = new ArrayList<>();
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].isBlank() && !isOperator(tokens[i].charAt(0))) {
				operandList.add(Integer.parseInt(tokens[i]));
			}
		}
		return operandList;
	}

	
	private boolean isOperator(char c) {
		return c == '(' || c == ')' || c == '+' 
				|| c == '-'	|| c == '*' || c == '/';
	}

	private String insertBlanks(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (isOperator(s.charAt(i))) {
				sb.append(" " + s.charAt(i) + " ");
			} else {
				sb.append(s.charAt(i));
			}
		}
		
		return sb.toString();
	}
}

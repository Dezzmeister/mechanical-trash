package com.dezzy.trash.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mathematical function with 1 input and 1 output.
 *
 * @author Joe Desmond
 */
public class Function {
	protected final char input;
	protected final char output;
	protected final String expression;
	protected final List<String> steps = new ArrayList<String>();
	protected Function derivative;
	
	public Function(char _input, char _output, String _expression) {
		input = _input;
		output = _output;
		expression = removeSpaces(addAsterisks(_input, _output, _expression));
	}
	
	private String addAsterisks(char in, char out, String expr) {
		String output = expr;
		
		for (int i = 0; i < output.length() - 1; i++) {
			char current = output.charAt(i);
			char next = output.charAt(i + 1);
			
			if ((((current >= '0' && current <= '9') || current == in || current == out) && (next == in || next == out)) || (current == ')' && next == '(')) {
				output = output.substring(0, i + 1) + "*" + output.substring(i + 1);
				
				i++;
			}
		}
		
		return output;
	}
	
	private String removeSpaces(String expr) {
		return expr.replace(" ", "");
	}
	
	public String findBaseOperation(char input, char output, String expression) {
		List<Integer> operators = findOperators(expression);
		if (operators.isEmpty()) {
			//derivative of expression is simple
		}
		
		return null;
	}
	
	private List<Integer> findOperators(String expr) {
		List<Integer> operators = new ArrayList<Integer>();
		
		for (int i = 0; i < expr.length(); i++) {
			if (isAnOperator(expr.charAt(i))) {
				operators.add(i);
			}
		}
		
		return operators;
	}
	
	private boolean isAnOperator(char c) {
		for (char operator : Relation.OPERATORS) {
			if (c == operator) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return expression;
	}
}

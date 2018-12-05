package com.dezzy.trash.util;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.trash.core.Function;
import com.dezzy.trash.core.Relation;

public class Formatter {
	
	public static ExpressionChain decompose(Function function) {
		return null;
	}
	
	public static String findBaseOperation(char input, char output, String expression) {
		List<Integer> operators = findOperators(expression);
		
		return null;
	}
	
	private static List<Integer> findOperators(String expr) {
		List<Integer> operators = new ArrayList<Integer>();
		
		for (int i = 0; i < expr.length(); i++) {
			if (isAnOperator(expr.charAt(i))) {
				operators.add(i);
			}
		}
		
		return operators;
	}
	
	private static boolean isAnOperator(char c) {
		for (char operator : Relation.OPERATORS) {
			if (c == operator) {
				return true;
			}
		}
		return false;
	}
}

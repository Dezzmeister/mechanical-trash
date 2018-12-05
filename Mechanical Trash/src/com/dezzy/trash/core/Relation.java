package com.dezzy.trash.core;


public enum Relation {	
	PRODUCT('*'),
	QUOTIENT('/'),
	SUM('+'),
	DIFFERENCE('-'),
	EXPONENT('^');
	
	public static final char[] OPERATORS = {'*','/','+','-','^'};
	public final char operator;
	
	private Relation(char _operator) {
		operator = _operator;
	}
}

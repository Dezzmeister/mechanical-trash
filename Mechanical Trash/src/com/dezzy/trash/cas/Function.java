package com.dezzy.trash.cas;

/**
 * Represents a mathematical function with one input and one output.
 *
 * @author Joe Desmond
 */
public class Function {
	public final char input;
	public final char output;
	public final String expr;
	
	/**
	 * Creates a function. If the function is not defined with an '=', then <code>_expr</code> is assumed
	 * to equal the output.
	 * 
	 * @param _input input variable of the function
	 * @param _output output variable of the function (function name)
	 * @param _expr the function
	 */
	public Function(char _input, char _output, String _expr) {
		input = _input;
		output = _output;
		expr = _expr;
	}
	
	public Function times(Function f) {
		return new Function(input, output, "(" + expr + ")*(" + f.expr + ")");
	}
	
	public Function dividedBy(Function f) {
		return new Function(input, output, "(" + expr + ")/(" + f.expr + ")");
	}
	
	public Function plus(Function f) {
		return new Function(input, output, "(" + expr + ")+(" + f.expr + ")");
	}
	
	public Function minus(Function f) {
		return new Function(input, output, "(" + expr + ")-(" + f.expr + ")");
	}
	
	public Function raisedTo(Function f) {
		return new Function(input, output, "(" + expr + ")^(" + f.expr + ")");
	}
}

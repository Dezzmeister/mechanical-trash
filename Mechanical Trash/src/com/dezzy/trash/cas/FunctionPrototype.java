package com.dezzy.trash.cas;

/**
 * Represents an unknown function with 1 input and 1 output; think of these as mathematical versions of function prototypes in C.
 *
 * @author Joe Desmond
 */
public class FunctionPrototype {
	protected final String input;
	protected final String output;
	
	public FunctionPrototype(String _output, String _input) {
		input = _input;
		output = _output;
	}
	
	/**
	 * Returns the input of this function. There can be only one input.
	 * 
	 * @return function input
	 */
	public String input() {
		return input;
	}
	
	/**
	 * Returns the output or name of this function. There can be only one output.
	 * 
	 * @return function output
	 */
	public String output() {
		return output;
	}
}

package com.dezzy.trash.cas;

/**
 * Represents an unknown function with 1 input and 1 output; think of these as mathematical versions of function prototypes in C.
 *
 * @author Joe Desmond
 */
public interface FunctionPrototype {
	
	/**
	 * Returns the input of this function. There can be only one input.
	 * 
	 * @return function input
	 */
	public String input();
	
	/**
	 * Returns the output or name of this function. There can be only one output.
	 * 
	 * @return function output
	 */
	public String output();
	
	/**
	 * Creates an anonymous <code>FunctionPrototype</code> with the specified input and output.
	 * 
	 * @param input function input
	 * @param output function output
	 * @return a new FunctionPrototype
	 */
	public static FunctionPrototype create(String input, String output) {
		return new FunctionPrototype() {
			@Override
			public String input() {
				return input;
			}
			
			@Override
			public String output() {
				return output;
			}
		};
	}
}

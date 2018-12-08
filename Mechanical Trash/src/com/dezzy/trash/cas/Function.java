package com.dezzy.trash.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a defined mathematical function with one input and one output. Unknown constants and other unknown functions
 * must be added 
 *
 * @author Joe Desmond
 */
public class Function implements FunctionPrototype {
	
	/**
	 * Predefined, reserved function names. New functions, constants, and variables cannot have any of these names:
	 * <ul>
	 * 	<li>ln
	 * 	<li>log
	 * 	<li>abs
	 * 	<li>sqrt
	 * 	<li>sin
	 * 	<li>cos
	 * 	<li>tan
	 * 	<li>csc
	 * 	<li>sec
	 * 	<li>cot
	 * 	<li>asin
	 * 	<li>acos
	 * 	<li>atan
	 * </ul>
	 */
	public static final List<String> PREDEFINED_FUNCTIONS = List.of("ln","log","abs","sqrt",
																    "sin","cos","tan","csc","sec","cot","asin","acos","atan");
	
	/**
	 * Predefined, reserved constants. New functions, constants, and variables cannot have any of these names:
	 * <ul>
	 * 	<li>e
	 * 	<li>i
	 * 	<li>pi
	 * </ul>
	 */
	public static final List<String> PREDEFINED_CONSTANTS = List.of("e","i","pi");
	
	private final List<String> constants = new ArrayList<String>();
	private final List<FunctionPrototype> functions = new ArrayList<FunctionPrototype>();
	
	private final String input;
	private final String output;
	
	/**
	 * The function definition.
	 */
	public final String expr;
	
	/**
	 * Creates a function. If the function is not defined with an '=', then <code>_expr</code> is assumed
	 * to equal the output.
	 * 
	 * @param _input input variable of the function
	 * @param _output output variable of the function (function name)
	 * @param _expr function definition
	 */
	public Function(String _input, String _output, String _expr) {
		input = _input;
		output = _output;
		expr = addImplicitOperators(_expr);
	}
	
	/**
	 * Creates a function. If the function is not defined with an '=', then <code>_expr</code> is assumed
	 * to equal the output. The name and input of the function are taken from <code>proto</code>.
	 * 
	 * @param proto input and output of function
	 * @param _expr function definition
	 */
	public Function(FunctionPrototype proto, String _expr) {
		this(proto.input(), proto.output(), _expr);
	}
	
	/**
	 * Adds implicit operators to a String containing a function definition. These operators are necessary
	 * for function decomposition.
	 * <br>
	 * Examples for a function with input "x" and output "y":
	 * <ul>
	 * 	<li>3x -> 3*x
	 * 	<li>xy -> x*y
	 *	<li>(x+1)(x-1) -> (x+1)*(x-1)
	 *	<li>lnx -> ln(x)
	 *	<li>sin^2x -> sin(x)^2
	 * </ul>
	 * 
	 * @param in function definition
	 * @return function definition without implicit operators
	 */
	public String addImplicitOperators(String in) {
		String out = in;
		
		return out;
	}
	
	private String addFunctionParentheses(String in) {
		String out = in;
		
		return null;
	}
	
	/**
	 * Tells this Function about unknown constants in its definition.
	 * <br>
	 * Example: <code>f=(a*x)/(b*x^3)</code> has 2 unknown constants, <code>a</code> and <code>b</code>
	 * 
	 * 
	 * @param assumedConstants all unknown constants in the Function
	 * @return this Function
	 */
	public Function assumeConstants(String ... assumedConstants) {
		constants.addAll(Arrays.asList(assumedConstants));
		
		return this;
	}
	
	/**
	 * Tells this Function about unknown functions in its definition.
	 * <br>
	 * Example: <code>f=g(x)+(1/x)</code> contains 1 unknown function, <code>g(x)</code>.
	 * <br>
	 * A {@link FunctionPrototype} to represent <code>g</code> would be defined with an input "x" and an output "g".
	 * 
	 * @param assumedFunctions all unknown functions in the Function
	 * @return this Function
	 */
	public Function assumeFunctions(FunctionPrototype ... assumedFunctions) {
		functions.addAll(Arrays.asList(assumedFunctions));
		
		return this;
	}
	
	/**
	 * Returns the list of assumed constants in this Function's definition.
	 * <br>
	 * Example: For a function <code>f=(a*x)/(b*x^3)</code>, returns <code>{a,b}</code>
	 * 
	 * @return this Function's assumed constants
	 */
	public List<String> assumedConstants() {
		return constants;
	}
	
	/**
	 * Returns the list of assumed functions in this Function's definition.
	 * <br>
	 * Example: For a function <code>f=g(x)+(1/x)</code>, returns a {@link FunctionPrototype} with input "x" and output "g".
	 * 
	 * @return this Function's assumed functions
	 */
	public List<FunctionPrototype> assumedFunctions() {
		return functions;
	}

	@Override
	public String input() {
		return input;
	}

	@Override
	public String output() {
		return output;
	}
}

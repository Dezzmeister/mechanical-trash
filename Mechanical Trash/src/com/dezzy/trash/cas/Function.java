package com.dezzy.trash.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a defined mathematical function with one input and one output. Unknown constants and other unknown functions
 * must be added via {@link Function#assumeConstants(String...) assumeConstants()} and 
 * {@link Function#assumeFunctions(FunctionPrototype...) assumeFunctions()}, then {@link Function#prepare() prepare()} must be called.
 *
 * @author Joe Desmond
 */
public class Function extends FunctionPrototype {
	
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
	
	private List<String> fullConstantsList = new ArrayList<String>();
	private List<String> fullFunctionsList = new ArrayList<String>();
	
	private String constantsRegex;
	private String functionsRegex;
	
	/**
	 * The function definition.
	 */
	private String expr;
	
	/**
	 * Creates a function. If the function is not defined with an '=', then <code>_expr</code> is assumed
	 * to equal the output.
	 * 
	 * @param _input input variable of the function
	 * @param _output output variable of the function (function name)
	 * @param _expr function definition
	 */
	public Function(String _output, String _input, String _expr) {
		super(_output, _input);
		expr = _expr;
	}
	
	/**
	 * Creates a function. If the function is not defined with an '=', then <code>_expr</code> is assumed
	 * to equal the output. The name and input of the function are taken from <code>proto</code>.
	 * 
	 * @param proto input and output of function
	 * @param _expr function definition
	 */
	public Function(FunctionPrototype proto, String _expr) {
		this(proto.output(), proto.input(), _expr);
	}
	
	/**
	 * Call this after this function and all unknowns have been set via {@link Function#assumeConstants(String...) assumeConstants()}
	 * and {@link Function#assumeFunctions(FunctionPrototype...) assumeFunctions()}.
	 * 
	 * @return this Function
	 */
	public Function prepare() {
		constantsRegex = generateConstantsRegex();
		functionsRegex = generateFunctionsRegex();
		
		expr = addImplicitOperators(expr);
		
		return this;
	}
	
	private String generateConstantsRegex() {
		String out = "(";
		
		PREDEFINED_CONSTANTS.forEach(fullConstantsList::add);
		constants.forEach(fullConstantsList::add);
		
		for (String constant : fullConstantsList) {
			out += "("+constant+")|";
		}
		
		//Remove trailing pipe and replace with close parenthesis
		return out.substring(0,out.length()-1) + ")";
	}
	
	private String generateFunctionsRegex() {
		String out = "(";
		
		PREDEFINED_FUNCTIONS.forEach(fullFunctionsList::add);
		functions.forEach(p -> fullFunctionsList.add(p.output()));
		
		for (String function : fullFunctionsList) {
			out += "("+function+")|";
		}
		
		//Remove trailing pipe and replace with close parenthesis
		return out.substring(0,out.length()-1) + ")";
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
		
		//out = addImplicitMultipliers(addFunctionParentheses(removeWhiteSpace(in)));
		
		out = addFunctionParentheses(removeWhiteSpace(in));
		return out;
	}
	
	private String addFunctionParentheses(String in) {
		String out = in;
		
		for (String fnName : PREDEFINED_FUNCTIONS) {
			String regex = "("+fnName+")(([\\d.]+)|("+input+"|"+output+"|"+constantsRegex+"))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(out);
			
			int count = 0;
			while (matcher.find()) {
				String sub = out.substring(matcher.start() + count,matcher.end() + count);
				
				String replacement = sub.substring(0,fnName.length()) + "(" + sub.substring(fnName.length()) + ")";
				out = out.replace(sub, replacement);
				
				//Because of added parentheses, offset by two for next match of same function name
				count += 2;
			}
		}
		
		return out;
	}
	
	private String addImplicitMultipliers(String in) {
		String out = in.replaceAll("[)][(]", ")*(");
		
		for (String fnName : PREDEFINED_FUNCTIONS) {
			//Crazy regex magic
			String regex = "((([\\d.]+)|"+constantsRegex+"|"+input+"|"+output+")("+constantsRegex+"|"+input+"|"+output+"|("+fnName+"[(]{1}([\\d.\\-\\+\\*\\/\\^]|"+input+"|"+constantsRegex+"|"+output+"|"+fnName+")+[)]{1})))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(out);
			
			Pattern subPattern = Pattern.compile("(("+fnName+"[(])|"+input+"|"+constantsRegex+"|"+output+"|[(])");
			
			int count = 0;
			while (matcher.find()) {
				String sub = out.substring(matcher.start() + count, matcher.end() + count);
				//System.out.println(sub);
				Matcher subMatcher = subPattern.matcher(sub);
				
				if (subMatcher.find()) {
					int index;
					
					if ((sub.indexOf(fnName) == sub.indexOf(input) + 1) || (sub.indexOf(fnName) == sub.indexOf(output) + 1)) {
						index = sub.indexOf(fnName);
					} else {
						index = subMatcher.start();
					}
					String replacement = "(" + sub.substring(0,index) + "*" + sub.substring(index) + ")";
					out = out.replace(sub, replacement);
					count += 3;
				}
			}
		}
		
		return out;
	}
	
	private String removeWhiteSpace(String in) {
		return in.replaceAll(" ", "");
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
	
	public List<String> fullConstantsList() {
		return fullConstantsList;
	}
	
	public List<String> fullFunctionsList() {
		return fullFunctionsList;
	}
	
	public String constantsRegex() {
		return constantsRegex;
	}
	
	public String functionsRegex() {
		return functionsRegex;
	}

	@Override
	public String input() {
		return input;
	}

	@Override
	public String output() {
		return output;
	}
	
	/**
	 * Returns the definition of this function.
	 * 
	 * @return function definition
	 */
	public String definition() {
		return expr;
	}
}

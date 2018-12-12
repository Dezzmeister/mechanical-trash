package com.dezzy.trash.cas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntermediateFunctionStructure {
	
	/**
	 * The original function whose decomposition is represented in <code>decomposedFunctions</code>
	 */
	public final Function root;
	
	private final Namer namer = new Namer("a");
	
	/**
	 * Contains a series of intermediate functions that, when substituted into each other, return the root function. Each function
	 * is a simple elementary operation containing functions, variables, or real numbers.
	 * <br>
	 * Example:
	 * <ul>
	 *	<li><code>f=a+b</code>
	 *	<li><code>a=3*x</code>
	 *	<li><code>b=x^x</code>
	 * </ul>
	 * Here, <code>f=(3*x)+(x^x)</code> is the root function.
	 */
	public final Map<FunctionPrototype, Optional<Function>> decomposedFunctions = new HashMap<FunctionPrototype, Optional<Function>>();
	
	private final List<FunctionPrototype> decomposedFunctionIndex = new ArrayList<FunctionPrototype>();
	
	/**
	 * Contains a list of constants in the root function.
	 */
	public final List<String> rootConstants;
	
	/**
	 * Contains a list of functions in the root function
	 */
	public final List<FunctionPrototype> rootFunctions;
	
	public IntermediateFunctionStructure(Function rootFunction) {
		root = rootFunction;
		rootConstants = rootFunction.assumedConstants();
		rootFunctions = rootFunction.assumedFunctions();
		
		decompose();
	}
	
	public void decompose() {
		resolveFunctionCalls();
	}
	
	private void resolveFunctionCalls() {
		String constantsVariablesRegex = "((" + root.input() + ")|(" + root.output() + ")|" + root.constantsRegex().substring(1);
		
		String regex = root.functionsRegex() + "[(]?([0-9.]|" + constantsVariablesRegex + ")+[)]?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(root.definition());
		
		System.out.println(getCompleteFunctionsRegex());
		
		while (matcher.find()) {
			System.out.println(root.definition().substring(matcher.start(), matcher.end()));
		}
	}
	
	private String getCompleteFunctionsRegex() {
		String decomposed = "(";
		
		for (FunctionPrototype p : decomposedFunctionIndex) {
			String fnName = p.output();
			
			decomposed += "("+fnName+")|";
		}
		
		String rootFunctionsRegex = root.functionsRegex();
		
		return decomposed + rootFunctionsRegex.substring(1);
	}
	
	private String getDecomposedFunctionsRegex() {
		String out = "";
		
		for (FunctionPrototype p : decomposedFunctionIndex) {
			String fnName = p.output();
			
			out += "("+fnName+")|";
		}
		
		return (out.isEmpty()) ? "" : "("+out.substring(0,out.length()-1)+")";
	}
	
	private class Namer {		
		private String nextFunctionName = "a";
		
		public Namer(String firstName) {
			nextFunctionName = firstName;
		}
		
		public String nextFunctionName() {
			while (!validateNextName()) {
				advanceLetterAt(nextFunctionName.length()-1);
			}
			
			return nextFunctionName;
		}
		
		private boolean validateNextName() {
			for (String s : rootConstants) {
				if (nextFunctionName.equals(s)) {
					return false;
				}
			}
			
			for (String s : Function.PREDEFINED_CONSTANTS) {
				if (nextFunctionName.equals(s)) {
					return false;
				}
			}
			
			for (String s : Function.PREDEFINED_FUNCTIONS) {
				if (nextFunctionName.equals(s)) {
					return false;
				}
			}
			
			for (int i = 0; i < decomposedFunctionIndex.size(); i++) {
				String fnName = decomposedFunctionIndex.get(i).output();
				
				if (nextFunctionName.equals(fnName)) {
					return false;
				}
			}
			
			for (int i = 0; i < rootFunctions.size(); i++) {
				String fnName = rootFunctions.get(i).output();
				
				if (nextFunctionName.equals(fnName)) {
					return false;
				}
			}
			
			return true;
		}
		
		private void advanceLetterAt(int position) {
			if (nextFunctionName.charAt(position) >= 'z') {
				nextFunctionName = nextFunctionName.substring(0, position) + "a" + nextFunctionName.substring(position + 1);
				
				if (position != 0) {
					advanceLetterAt(--position);
				} else {
					nextFunctionName += "a";
				}
			} else {
				nextFunctionName = nextFunctionName.substring(0, position) + (char)(nextFunctionName.charAt(position)+1) + nextFunctionName.substring(position + 1);
			}
		}
	}
}

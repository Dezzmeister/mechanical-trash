package com.dezzy.trash.cas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		
	}
	
	private class Namer {
		private String nextFunctionName = "a";
		
		public Namer(String firstName) {
			nextFunctionName = firstName;
			
			if (!validateNextName()) {
				//generate new name
			}
		}
		
		private boolean validateNextName() {
			for (String s : rootConstants) {
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
			
			//for (int i = 0; i < ro)
			
			return true;
		}
	}
}

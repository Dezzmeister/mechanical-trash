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
	
	/**
	 * Contains a list of constants in the root function.
	 */
	public final List<String> constants = new ArrayList<String>();
	
	public IntermediateFunctionStructure(Function rootFunction) {
		root = rootFunction;
		decompose();
	}
	
	public void decompose() {
		
	}
	
	private class Namer {
		
	}
}

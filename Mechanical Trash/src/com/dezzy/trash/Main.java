package com.dezzy.trash;

import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.interfaces.IExpr;

public class Main {
	
	public static void main(String[] args) {
		/*
		Function function = new Function('x', 'y', "y = 4xy + 1 + (x - 3)(y + 4)");
		System.out.println(function);
		*/
		EvalUtilities util = new EvalUtilities();
		IExpr result;
		
		result = util.evaluate("D(ArcTan(x,y),x)");
		System.out.println(result);
	}
}

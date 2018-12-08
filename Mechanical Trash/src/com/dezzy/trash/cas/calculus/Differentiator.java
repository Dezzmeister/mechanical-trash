package com.dezzy.trash.cas.calculus;

import com.dezzy.trash.cas.Function;

public class Differentiator {
	
	public Function derivative(Function f) {
		return null;
	}
	
	/**
	 * Finds the derivative of a product of two functions using the Product Rule. Both functions
	 * must have the same input and output.
	 * 
	 * @param f0 first function
	 * @param f1 second function
	 * @return derivative of (f0*f1)(input)
	 */
	public Function derivativeOfProduct(Function f0, Function f1) {
		return (derivative(f0).times(f1)).plus(f0.times(derivative(f1)));
	}
	
	/**
	 * Finds the derivative of a quotient of two functions using the Product Rule. Both functions
	 * must have the same input and output.
	 * 
	 * @param f0 first function
	 * @param f1 second function
	 * @return derivative of (f0/f1)(input)
	 */
	public Function derivativeOfQuotient(Function f0, Function f1) {
		return ((derivative(f0).times(f1)).minus(f0.times(derivative(f1)))).dividedBy(f1.times(f1));
	}
	
	/**
	 * Finds the derivative of a composition of two functions using the Chain Rule.
	 * 
	 * @param f0 first function
	 * @param f1 second function
	 * @return derivative of f0(f1(input))
	 */
	public Function derivativeOfChain(Function f0, Function f1) {
		//crap
		return null;
	}
}

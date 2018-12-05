package com.dezzy.trash;

import com.dezzy.trash.core.Function;

public class Main {
	
	public static void main(String[] args) {
		Function function = new Function('x', 'y', "y = 4xy + 1 + (x - 3)(y + 4)");
		System.out.println(function);
	}
}

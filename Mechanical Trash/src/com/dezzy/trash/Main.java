package com.dezzy.trash;

import com.dezzy.trash.cas.Function;

public class Main {
	
	public static void main(String[] args) {
		Function test = new Function("x","y","3x + ln5.3461-24*sin3.1563-sin45-(x-1)(x+1)/sinx").prepare();
		
		System.out.println(test.definition());
	}
}

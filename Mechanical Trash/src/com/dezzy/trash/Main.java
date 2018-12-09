package com.dezzy.trash;

import com.dezzy.trash.cas.Function;

public class Main {
	
	public static void main(String[] args) {
		//Function test0 = new Function("x","y","3x + ln5.3461-24*sin3.1563-sin45-(x-1)(x+1)/sinx+7y/4(x-3)").prepare();
		Function test1 = new Function("x","y","3x + 4sin45 - ysiny + sin(lnx)").assumeConstants("a").prepare();
		
		System.out.println(test1.definition());
	}
}

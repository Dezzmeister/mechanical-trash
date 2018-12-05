package com.dezzy.trash.util;

import java.util.ArrayList;
import java.util.List;

import com.dezzy.trash.core.Expression;
import com.dezzy.trash.core.Relation;

public class ExpressionChain {
	public List<ExpressionChain> expressions = new ArrayList<ExpressionChain>();
	public List<Relation> relations = new ArrayList<Relation>();
	public Expression expression;
	
	public ExpressionChain(ExpressionChain first) {
		expressions.add(first);
	}
	
	public ExpressionChain(Expression _expression) {
		expression = _expression;
	}
	
	public ExpressionChain chain(Relation relation, ExpressionChain expression) {
		relations.add(relation);
		expressions.add(expression);
		
		return this;
	}
	
	public ExpressionChain removeLast() {
		relations.remove(relations.size()-1);
		
		return expressions.remove(expressions.size()-1);
	}
}

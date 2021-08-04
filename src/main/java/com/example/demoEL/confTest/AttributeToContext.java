package com.example.demoEL.confTest;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class AttributeToContext {
    String name;
    String expression;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    public void addAttributeContext(StandardEvaluationContext context){
        ExpressionParser parser = new SpelExpressionParser();
        Object valueResult = parser.parseExpression(expression).getValue(context);
        context.setVariable(name, valueResult);
    }
}

package com.example.demoEL.confTest;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CalculationRule {

    String attName;
    String expression;
    CalculationRule inner;

    List<AttributeToContext> attributes = new ArrayList<>();


    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public CalculationRule getInner() {
        return inner;
    }

    public void setInner(CalculationRule inner) {
        this.inner = inner;
    }

    public List<AttributeToContext> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeToContext> attributes) {
        this.attributes = attributes;
    }

    public void executeCalculation(StandardEvaluationContext context, Record record) {
        // la llamada interna se realiza posterior al calculo de quien la contiene
        // Utilizar la Spel de Spring para obtener el dato calculado
        attributes.stream().forEach(attributeToContext -> attributeToContext.addAttributeContext(context));
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctxRecord = new StandardEvaluationContext(record);
        Object valueResult = parser.parseExpression(expression).getValue(context);
        parser.parseExpression(attName).setValue(ctxRecord, valueResult);
        if (inner != null) {
            inner.executeCalculation(context, record);
        }
    }

    public Map<String, Object> executeCalculationReturn(StandardEvaluationContext context) {
        // la llamada interna se realiza posterior al calculo de quien la contiene
        // Utilizar la Spel de Spring para obtener el dato calculado
        Map<String, Object> result;
        attributes.stream().forEach(attributeToContext -> attributeToContext.addAttributeContext(context));
        ExpressionParser parser = new SpelExpressionParser();
        Object valueResult = parser.parseExpression(expression).getValue(context);
        parser.parseExpression(attName).setValue(context, valueResult);
        result = parser.parseExpression("[recordDest]").getValue(context, Map.class);
        if (inner != null) {
            result = inner.executeCalculationReturn(context);
        }
        return result;
    }


    public static Map<String, Object> sameRecord(Map<String, Object> u, Map<String, Object> u1) {
         u.putAll(u1);
         return u;
    }
}

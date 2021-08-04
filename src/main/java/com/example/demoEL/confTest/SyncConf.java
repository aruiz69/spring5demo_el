package com.example.demoEL.confTest;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SyncConf {


    private List<CalculationRule> calcAtt;
    private String productName;

    public void execCalculationRule(Map beansMap, List<Record> records) {
        calcAtt.parallelStream().forEach(calculationRule -> records.parallelStream()
                .forEach(record -> {
                    Map<String, Object> localMapBeans = new HashMap<>(beansMap);
                    localMapBeans.put("record", record.clone());
                    StandardEvaluationContext contextCalculationRules = new StandardEvaluationContext(localMapBeans);
                    calculationRule.executeCalculation(contextCalculationRules, record);
                }));
    }

    public List<Map<String, Object>> execCalculationRule1(Map beansMap, List<Record> records) {
       return records.parallelStream()
                .map(record ->
                        calcAtt.parallelStream().reduce(new HashMap<>(), (parRecord, calculationRule) -> {
                            Map<String, Object> localMapBeans = new HashMap<>(beansMap);
                            localMapBeans.put("recordOri", record);
                            localMapBeans.put("recordDest", new HashMap<String, Object>());
                            StandardEvaluationContext contextCalculationRules = new StandardEvaluationContext(localMapBeans);
                            return  calculationRule.executeCalculationReturn(contextCalculationRules);
                        },CalculationRule::sameRecord) ).collect(Collectors.toList());
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<CalculationRule> getCalcAtt() {
        return calcAtt;
    }

    public void setCalcAtt(List<CalculationRule> calcAtt) {
        this.calcAtt = calcAtt;
    }
}

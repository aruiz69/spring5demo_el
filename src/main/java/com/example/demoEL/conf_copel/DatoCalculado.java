package com.example.demoEL.conf_copel;

import com.example.demoEL.confTest.AttributeToContext;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    Esta clase corresponde a determinar el cálculo requerido por el mapeo entre el registro de entrado y la información de salida
    Utiliza Expression Language de Spring para realizar el cálculo, para mayor detalle
    @see <a href="https://docs.spring.io/spring-framework/docs/4.2.0.RELEASE/spring-framework-reference/html/expressions.html">Spring expressions</a>
    Los objetos aquí tratados se utilizan desde una perspectiva de un Mapa, contenido en el contexto.
 */
@Data
@Getter
@Setter
public class DatoCalculado {
    private String expresionDeCalculo;
    private String atributoCalculado;
    private DatoCalculado datoCalculado; // Atributo calculado en la misma transacción, opera sobre el mismo mapa
    private List<AttributeToContext> valoresAdicionales = new ArrayList<>();
    private List<MapaDeDatos> mapaDeDatosInterno = null;  // Se considera un objeto complejo a agregarse en el resultado del mapeo,
    // Se considera que regresará solo un objeto complejo, tomando como base el primer mapa que
    // Se regresa del proceso  aplicarMapaDeDatos

    /**
     * @param context    Contexto a utilizarse para las Expresiones de tipo Spring
     * @param nombreMapa Identificador del Mapa de salida, para agregar los atributos de salida
     * @return Mapa resultante con los cálculos y atributos que fueron configurados
     */
    public Map<String, Object> aplicarMapa(StandardEvaluationContext context, String nombreMapa) {
        ExpressionParser parser = new SpelExpressionParser();

        // Proceso que permite agregar variable calculada en el contexto, para utilizarse en el proceso del mapeo del cálculo
        valoresAdicionales.forEach(att -> {
            ExpressionParser expressionParser = new SpelExpressionParser();
            Object resultadoAtt = expressionParser.parseExpression(att.getExpression()).getValue(context);
            context.setVariable(att.getName(), resultadoAtt);
        });

        Object mapeoResultado = parser.parseExpression(expresionDeCalculo).getValue(context);
        parser.parseExpression(atributoCalculado).setValue(context, mapeoResultado);
        Map<String, Object> resultado = parser.parseExpression("[" + nombreMapa + "]").getValue(context, Map.class);

        if (datoCalculado != null) {
            datoCalculado.aplicarMapa(context, nombreMapa);
        }

        if (mapaDeDatosInterno != null) {
            Map<String, Object> mapResult = getStringObjectMap(mapaDeDatosInterno, context);
            if (mapResult != null) {
                resultado.putAll(mapResult);
            }
        }
        return resultado;
    }

    private Map<String, Object> getStringObjectMap(List<MapaDeDatos> mapaDeDatos, StandardEvaluationContext context) {
        Map<String, Object> mapResult = new HashMap<>();
        if (mapaDeDatos != null) {
            mapaDeDatos.stream().forEach(mapa -> {
                        List<Map<String, Object>> maps = mapa.aplicarMapaDeDatos(context);
                        if (maps != null) {
                            maps.stream().forEach(map ->
                                    mapResult.put(mapa.getNombreMapeo(), map)
                            );
                        }
                    }
            );
        }
        return mapResult;
    }

}


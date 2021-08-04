package com.example.demoEL.conf_copel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
   Esta clase corespnde a un mapeo de datos entre el registro obtenido y la configuración
   que se le establezca através de datos de caldulos requeridos
 */
@Data
@Getter
@Setter
public class MapaDeDatos {
    private String NombreMapeo;
    private List<DatoCalculado> datoCalculados;
    private List<MapaDeDatos> mapaDeMultipleSalidas = new ArrayList<>();
    //Cuando se require más de un mapeo para el mismo registro,
    // se considera independiente uno de otro

    /**
     * @return Lista de Mapas, cada entrada corresponde a un registro de salida, de acuerdo a la configuración que se establezca
     */
    public List<Map<String, Object>> aplicarMapaDeDatos(StandardEvaluationContext context) {

        if (mapaDeMultipleSalidas.isEmpty()) {
            mapaDeMultipleSalidas.add(this);
        }
        return mapaDeMultipleSalidas
                .stream()
                .map(datoCalculados ->
                        datoCalculados.datoCalculados
                                .parallelStream()
                                .reduce(new HashMap<>(), (parRecord, datoCalculado) -> {
                                    Object mapa = context.getRootObject().getValue();
                                    if (mapa instanceof Map) {
                                        ((Map<String, Object>) mapa).put(getNombreMapeo(), new ConcurrentHashMap<>());
                                    }
                                    return datoCalculado.aplicarMapa(context, getNombreMapeo());

                                }, MapaDeDatos::uneMapas))
                .collect(Collectors.toList());
    }

    /*
        Metódo de apoyo para el proceso de reducción a realizarse por el proceso aplicar Mapa de Datos
     */
    public static Map<String, Object> uneMapas(Map<String, Object> mapaAquienUne, Map<String, Object> mapaParaUnir) {
        mapaAquienUne.putAll(mapaParaUnir);
        return mapaAquienUne;
    }
}
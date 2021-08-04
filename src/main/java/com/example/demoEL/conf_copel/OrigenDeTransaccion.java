package com.example.demoEL.conf_copel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Esta clase permite establecer la configuración del origen de datos
 *  se parte que existe una tabla con los datos que serán transformados (calculando sus atributos de salida)
 */
@Data
@Setter
@Getter
public class OrigenDeTransaccion {
    List<String> columnasRequeridas;
    String tabla;
    String predicado;
    Map<String, String> columnasParaOrdenar;
    MapaDeDatos mapaDeDatos;

}





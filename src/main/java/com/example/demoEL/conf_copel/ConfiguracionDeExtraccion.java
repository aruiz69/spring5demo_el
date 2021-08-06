package com.example.demoEL.conf_copel;

import com.example.demoEL.conf_copel.OrigenDeTransaccion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;
import java.util.Map;

@Data
@Setter
@Getter
class ConfiguracionDeExtraccion {
    String empresa;
    String productoServicio;
    GregorianCalendar fechaMinimaExtraccion;
    String fechaMaximaExtraccion;
    Map<String, OrigenDeTransaccion> origenDeTransacciones;
}

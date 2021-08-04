package com.example.demoEL.conf_copel;

import com.example.demoEL.conf_copel.OrigenDeTransaccion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Setter
@Getter
class TransaccionesSoportadas {
    Map<String, OrigenDeTransaccion> origenDeTransacciones;
}

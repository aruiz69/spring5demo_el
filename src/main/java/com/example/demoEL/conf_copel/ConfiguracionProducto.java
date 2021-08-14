package com.example.demoEL.conf_copel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ConfiguracionProducto {
    ConfiguracionDeExtraccion configuracionDeExtraccion;
    ConfiguracionMapeoDeAtributos configuracionMapeoDeAtributos;
    ConfiguracionRecepcionOmnicanal configuracionRecepcionOnicanal;
}

@Data
@Setter
@Getter
class ConfiguracionRecepcionOmnicanal{

}

@Data
@Setter
@Getter
class ConfiguracionMapeoDeAtributos {

}
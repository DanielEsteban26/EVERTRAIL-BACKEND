package pe.edu.cibertec.evertrailbackend.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MensajeResponse  implements Serializable {

    private String mensaje;
    private Object object;

}

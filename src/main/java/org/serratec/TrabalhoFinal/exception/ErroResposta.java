package org.serratec.TrabalhoFinal.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroResposta {

        private Integer status;
        private String mensagem;
        private LocalDateTime timestamp;
        private List<String> erros;
}
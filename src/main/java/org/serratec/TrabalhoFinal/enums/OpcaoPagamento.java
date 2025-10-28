package org.serratec.TrabalhoFinal.enums;

import org.serratec.TrabalhoFinal.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;



public enum OpcaoPagamento {
	
	PIX,
	DEBITO,
	CREDITO,
	DOISCARTAO,
	BOLETO,
	DINHEIRO;
	
	
	@JsonCreator
	public static OpcaoPagamento verifica(String value) throws EnumValidationException {
		for (OpcaoPagamento c: values()) {
			if (value.equals(c.name())) {
				return c;
			}
		}
		throw new EnumValidationException("Informação de Pagamento preenchida incorretamente");
	}

}

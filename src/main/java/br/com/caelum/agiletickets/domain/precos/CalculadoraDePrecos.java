package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA)) {
			preco = calculaPrecoCinemaShow(sessao);
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoCinemaShow(sessao);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPrecoBalletOrquestra(sessao);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPrecoBalletOrquestra(sessao);
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		preco = calculaPreco(sessao, 0.50, 0.20);
		
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		preco = calculaPreco(sessao, 0.05, 0.10);
		return preco;
	}

	private static BigDecimal calculaPreco(Sessao sessao, Double limiarParaAumento, Double percentualDeAumento) {
		BigDecimal preco;
		//quando estiver acabando os ingressos... 
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= limiarParaAumento) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(percentualDeAumento)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}
	
}
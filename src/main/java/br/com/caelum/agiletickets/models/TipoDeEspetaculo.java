package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {
	CINEMA(0.05, 0.10, 0.0), SHOW(0.05, 0.10, 0.0), TEATRO(1.0, 0.0, 0.0), BALLET(0.50, 0.20, 0.10), ORQUESTRA(0.50, 0.20, 0.10);
	
	private Double limiarAumento;
	private Double percentualAumento;
	private Double percentualDeAumentoDuracaoMaiorQue60Minutos;
	
	private TipoDeEspetaculo(Double limiarParaAumento, Double percentualDeAumento, Double percentualDeAumentoPorTempo) {
		limiarAumento = limiarParaAumento;
		percentualAumento = percentualDeAumento;
		percentualDeAumentoDuracaoMaiorQue60Minutos = percentualDeAumentoPorTempo;
	}
	
	public BigDecimal calculaPreco(Sessao sessao) {
		BigDecimal preco;
		//quando estiver acabando os ingressos... 
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= limiarAumento) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(percentualAumento)));
		} else {
			preco = sessao.getPreco();
		}
		
		if(sessao.getDuracaoEmMinutos() != null && sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(this.percentualDeAumentoDuracaoMaiorQue60Minutos)));
		}
		
		return preco;
	}
}

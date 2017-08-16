package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void ValidarPeriodicidadeDiaria() {
		Espetaculo ivete = new Espetaculo();

		LocalDate diaInicio = new LocalDate();
		LocalDate diaFim = diaInicio.plusDays(3);

		LocalTime hora = new LocalTime();

		List<Sessao> listSessoes = ivete.criaSessoes(diaInicio, diaFim, hora, Periodicidade.DIARIA);

		assertEquals(4, listSessoes.size());
	}

	@Test
	public void ValidarPeriodicidadeSemanal() {
		Espetaculo ivete = new Espetaculo();

		LocalDate diaInicio = new LocalDate();
		LocalDate diaFim = diaInicio.plusDays(30);

		LocalTime hora = new LocalTime();

		List<Sessao> listSessoes = ivete.criaSessoes(diaInicio, diaFim, hora, Periodicidade.SEMANAL);

		assertEquals(5, listSessoes.size());
	}

	@Test
	public void ValidarSessaoCriadaDiaria() {
		Espetaculo ivete = new Espetaculo();
		LocalDate diaInicio = new LocalDate();
		LocalDate diaFim = new LocalDate();
		LocalTime hora = new LocalTime();

		List<Sessao> listSessoes = ivete.criaSessoes(diaInicio, diaFim, hora, Periodicidade.DIARIA);

		Sessao sessao = listSessoes.get(0);

		String diaIni = diaInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR")));
		String horaIni = hora.toString(DateTimeFormat.shortTime().withLocale(new Locale("pt", "BR")));

		assertEquals(sessao.getDia(), diaIni);
		assertEquals(sessao.getHora(), horaIni);
	}

	@Test
	public void ValidarSessoesCriadasSemanal() {
		Espetaculo ivete = new Espetaculo();
		LocalDate diaInicio = new LocalDate();
		LocalDate diaFim = diaInicio.plusDays(30);
		LocalTime hora = new LocalTime();

		List<Sessao> listSessoes = ivete.criaSessoes(diaInicio, diaFim, hora, Periodicidade.SEMANAL);

		Iterator<Sessao> it = listSessoes.iterator();

		String diaIni = diaInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR")));
		String horaIni = hora.toString(DateTimeFormat.shortTime().withLocale(new Locale("pt", "BR")));

		assertEquals(5, listSessoes.size());

		while (it.hasNext()) {
			Sessao s = (Sessao) it.next();

			assertEquals(diaIni, s.getDia());
			assertEquals(horaIni, s.getHora());

			diaInicio = diaInicio.plusDays(7);
			diaIni = diaInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR")));

		}

	}

	@Test(expected = DataInvalidaException.class)
	public void ValidarSessaoInvalida() {
		Espetaculo ivete = new Espetaculo();

		LocalDate diaFim = new LocalDate();
		LocalDate diaInicio = diaFim.plusDays(1);

		LocalTime hora = new LocalTime();

		ivete.criaSessoes(diaInicio, diaFim, hora, Periodicidade.DIARIA);

	}

}

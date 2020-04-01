package br.com.michael.robowindows.usecase.reproduzir;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoReproduzir {

	public String tipoEvento = null;
	public String evento = null;
	public String tecla = null;
	public int x = 0;
	public int y = 0;
	public String botao = null;
	public long espera = 5;
	
	public EventoReproduzir() {
	}
	
	public EventoReproduzir(String evento, String tecla, long espera) {
		tipoEvento = "tecla";
		this.evento = evento;
		this.tecla = tecla;
		this.espera = espera;
	}
	
	public EventoReproduzir(String evento, int x, int y, String botao, long espera) {
		tipoEvento = "mouse";
		this.evento = evento;
		this.x = x;
		this.y = y;
		this.botao = botao;
		this.espera = espera;
	}

}

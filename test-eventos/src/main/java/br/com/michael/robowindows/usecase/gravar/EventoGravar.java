package br.com.michael.robowindows.usecase.gravar;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoGravar {

	public String tipoEvento = null;
	public String evento = null;
	public String tecla = null;
	public int x = 0;
	public int y = 0;
	public String botao = null;
	public long espera = 5;
	
	public EventoGravar() {
	}
	
	public EventoGravar(String evento, String tecla) {
		tipoEvento = "tecla";
		this.evento = evento;
		this.tecla = tecla;
	}
	
	public EventoGravar(String evento, int x, int y, String botao) {
		tipoEvento = "mouse";
		this.evento = evento;
		this.x = x;
		this.y = y;
		this.botao = botao;
	}
}

package br.com.michael.robowindows.usecase.gravar;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.michael.robowindows.dataprovider.gravar.Gravador;

@Component
public class GravadorDeEvento extends Gravador {
	
	public interface OuvinteGravador {
		public void eventoGravado(EventoGravar eventoGravado);
	}

	private long tempo = 0;
	private Queue<EventoGravar> eventos = new LinkedBlockingQueue<EventoGravar>();
	private List<OuvinteGravador> ouvintes = new LinkedList<OuvinteGravador>();
	private boolean pausado = false;
	
	public void adicionarOuvinte(OuvinteGravador ouvinte) {
		this.ouvintes.add(ouvinte);
	}

	@Override
	protected void adicionarEvento(String tipoEvento, NativeInputEvent nativeEvent) {
		EventoGravar eventoGravar = null;
		if (nativeEvent instanceof NativeKeyEvent) {
			NativeKeyEvent keyEvent = ((NativeKeyEvent) nativeEvent);
			eventoGravar = new EventoGravar(tipoEvento, String.valueOf(keyEvent.getRawCode()));
		} else if (nativeEvent instanceof NativeMouseEvent) {
			NativeMouseEvent mouseEvent = ((NativeMouseEvent) nativeEvent);
			eventoGravar = new EventoGravar(tipoEvento, mouseEvent.getX(), mouseEvent.getY(),
					NativeKeyEvent.getKeyText(mouseEvent.getButton()));
		}

		long tempoCorrente = System.currentTimeMillis();
		eventoGravar.espera = tempoCorrente - tempo;
		gravarEvento(eventoGravar);
		tempo = tempoCorrente;
	}

	public void gravar() throws Exception {
		tempo = System.currentTimeMillis();
		inicarEscuta();
	}

	public void pausa(String comando) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.hasNext() && sc.nextLine().startsWith(comando))
				break;
		}
		sc.close();
	}
	
	public void pausar() throws Exception {
		if(pausado)
			pausarEscuta();
		else
			inicarEscuta();
		
		pausado = !pausado;
	}

	public void parar(File arquivo) throws Exception {
		pausar();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(arquivo, eventos);
	}
	
	protected void gravarEvento(EventoGravar eventoGravado) {
		eventos.add(eventoGravado);
		for(OuvinteGravador ouvinte : ouvintes)
			ouvinte.eventoGravado(eventoGravado);
	}

}

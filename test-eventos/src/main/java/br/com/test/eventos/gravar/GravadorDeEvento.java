package br.com.test.eventos.gravar;

import java.io.File;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GravadorDeEvento extends Gravador {

	private long tempo = 0;
	private Queue<EventoGravar> eventos = new LinkedBlockingQueue<EventoGravar>();

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
		eventos.add(eventoGravar);
		tempo = tempoCorrente;
	}

	public void gravar() throws Exception {
		tempo = System.currentTimeMillis();
		iniciar();
	}

	public void pausa(String comando) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.hasNext() && sc.nextLine().startsWith(comando))
				break;
		}
		sc.close();
	}

	public void salvar(File arquivo) throws Exception {
		finalizar();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(arquivo, eventos);
	}

}

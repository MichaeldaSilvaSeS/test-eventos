package br.com.michael.robowindows.dataprovider.reprodutor;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.michael.robowindows.usecase.reproduzir.EventoReproduzir;

public class Reprodutor {
	
	static {
		System.setProperty("java.awt.headless", "false");
	}

	public List<EventoReproduzir> lerEventos(File arquivo) throws Exception {
		return new ObjectMapper().readValue(arquivo, new TypeReference<List<EventoReproduzir>>(){});
	}
	
	public void interpretarEventoMouse(Robot robot, EventoReproduzir evento) {
		if(evento.evento.equals("move"))
			robot.mouseMove(evento.x, evento.y);
		if(evento.evento.equals("click")) {
			robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
		}
	}
	
	public void interpretarEventoTecla(Robot robot, EventoReproduzir evento) {
		if(evento.evento.equals("pressionado")) {
			char codigo = (char) (Integer.valueOf(evento.tecla)).intValue();
			
			if(codigo == 13) {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}else { 
				if(codigo == 91) {
					robot.keyPress(KeyEvent.VK_WINDOWS);
					robot.keyRelease(KeyEvent.VK_WINDOWS);
				}else if(codigo == 120) {
					robot.keyPress(KeyEvent.VK_F9);
					robot.keyRelease(KeyEvent.VK_F9);
				}
				else {
					robot.keyPress(codigo);
					robot.keyRelease(codigo);	
				}
			}
		}
	}
}

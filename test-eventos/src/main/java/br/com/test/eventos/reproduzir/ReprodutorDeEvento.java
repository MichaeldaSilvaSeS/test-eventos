package br.com.test.eventos.reproduzir;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReprodutorDeEvento {
	private List<EventoReproduzir> eventos;
	
	public void reproduzir(File arquivo) throws JsonParseException, JsonMappingException, IOException, AWTException {
		ObjectMapper objectMapper = new ObjectMapper();
		this.eventos = objectMapper.readValue(arquivo, new TypeReference<List<EventoReproduzir>>(){});
		interpretar();
	}
	
	public void interpretar() throws AWTException {
		System.setProperty("java.awt.headless", "false");
		Robot robot = new Robot();
		
		eventos.stream().forEach(evento ->{
			if(evento.tipoEvento.equals("mouse") )
				interpretarEventoMouse(robot,evento);
			else if(evento.tipoEvento.equals("tecla")) 
				interpretarEventoTecla(robot,evento);
			
			try {
				Thread.sleep(evento.espera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void interpretarEventoMouse(Robot robot, EventoReproduzir evento) {
		if(evento.evento.equals("move"))
			robot.mouseMove(evento.x, evento.y);
		if(evento.evento.equals("click")) {
			robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
		}
	}
	
	private void interpretarEventoTecla(Robot robot, EventoReproduzir evento) {
		if(evento.evento.equals("pressionado")) {
			char codigo = (char) (Integer.valueOf(evento.tecla)).intValue();
			
			if(codigo == 13) {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}else { 
				if(codigo == 91) {
					robot.keyPress(KeyEvent.VK_WINDOWS);
					robot.keyRelease(KeyEvent.VK_WINDOWS);
				}
				else {
					robot.keyPress(codigo);
					robot.keyRelease(codigo);	
				}
			}
		}
	}

}

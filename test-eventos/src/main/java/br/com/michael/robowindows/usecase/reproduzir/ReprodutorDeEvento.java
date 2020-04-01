package br.com.michael.robowindows.usecase.reproduzir;
import java.awt.Robot;
import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.michael.robowindows.dataprovider.reprodutor.Reprodutor;

@Component
public class ReprodutorDeEvento {
	
	private Reprodutor reprodutor = new Reprodutor();
	
	public void reproduzir(File arquivo) throws Exception {
		List<EventoReproduzir> eventos = reprodutor.lerEventos(arquivo);
		
		Robot robot = new Robot();		
		eventos.stream().forEach(evento ->{
			if(evento.tipoEvento.equals("mouse") )
				reprodutor.interpretarEventoMouse(robot,evento);
			else if(evento.tipoEvento.equals("tecla")) 
				reprodutor.interpretarEventoTecla(robot,evento);
			
			try {
				Thread.sleep(evento.espera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}

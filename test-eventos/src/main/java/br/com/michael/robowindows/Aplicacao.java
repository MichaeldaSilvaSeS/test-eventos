package br.com.michael.robowindows;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.michael.robowindows.entrypoint.cli.RoboCLI;
import br.com.michael.robowindows.entrypoint.gui.RoboGUI;

@SpringBootApplication
public class Aplicacao {

	public static void main(String[] args) {
		if(args.length > 0)
			new RoboCLI().exibir(args);
		else
			new RoboGUI().exibir();
	}
}

package br.com.michael.robowindows.entrypoint.cli;

import org.springframework.boot.SpringApplication;

import br.com.michael.robowindows.Aplicacao;

public class RoboCLI {

	public void exibir(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}
}

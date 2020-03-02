package br.com.test.eventos.gravar;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=2)
@Component
public class GravadorRunner implements ApplicationRunner{
	
	@Autowired
	private GravadorDeEvento gravadorDeEvento;
	
	public void run(ApplicationArguments args) throws Exception {
		if(!args.getOptionNames().contains("gravar"))
			return;
		
		if(!args.getOptionNames().contains("arquivo") || args.getOptionValues("arquivo").size() == 0) {
			System.out.println("=============================");
			System.out.println("Parametro --arquivo obrigatorio");
			System.out.println("=============================");
			return;
		}
		
		String caminhoArquivo = args.getOptionValues("arquivo").get(0);
		
		System.out.println("Gravando... ");
		gravadorDeEvento.gravar();
		
		System.out.print("Digite exit para encerrar a gravacao: ");
		gravadorDeEvento.pausa("exit");
		
		System.out.println("Salvando... ");
		gravadorDeEvento.salvar(new File(caminhoArquivo));
		
		System.exit(0);
	}

}

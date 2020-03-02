package br.com.test.eventos.reproduzir;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=1)
@Component
public class ReprodutorRunner implements ApplicationRunner{
	
	@Autowired
	private ReprodutorDeEvento reprodutorDeEvento;
	
	public void run(ApplicationArguments args) throws Exception {
		if(!args.getOptionNames().contains("reproduzir"))
			return;
		
		if(!args.getOptionNames().contains("arquivo") || args.getOptionValues("arquivo").size() == 0) {
			System.out.println("=============================");
			System.out.println("Parametro --arquivo obrigatorio");
			System.out.println("=============================");
			return;
		}

		File arquivo = new File(args.getOptionValues("arquivo").get(0));
		if(!(arquivo.exists() || arquivo.canRead() || arquivo.isFile()) ) {
			System.out.println("=============================");
			System.out.println("Parametro --arquivo invalido");
			System.out.println("=============================");
			return;
		}

		System.out.println("Reproduzindo... ");
		reprodutorDeEvento.reproduzir(arquivo);
		System.exit(0);
	}

}

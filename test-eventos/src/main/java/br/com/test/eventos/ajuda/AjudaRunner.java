package br.com.test.eventos.ajuda;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=3)
@Component
public class AjudaRunner implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(args.getOptionNames().contains("reproduzir") || args.getOptionNames().contains("gravar"))
			return;
			
		System.out.println("=============================");
		System.out.println("Parametro --reproduzir ou --gravar ");
		System.out.println("=============================");
	}

}

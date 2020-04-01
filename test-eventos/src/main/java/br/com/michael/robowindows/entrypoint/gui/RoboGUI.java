package br.com.michael.robowindows.entrypoint.gui;

import java.awt.EventQueue;
import java.io.File;

import br.com.michael.robowindows.entrypoint.gui.TelaPrincipcal.InteracaoTela;
import br.com.michael.robowindows.usecase.gravar.EventoGravar;
import br.com.michael.robowindows.usecase.gravar.GravadorDeEvento;
import br.com.michael.robowindows.usecase.gravar.GravadorDeEvento.OuvinteGravador;
import br.com.michael.robowindows.usecase.reproduzir.ReprodutorDeEvento;

public class RoboGUI {

	private class InteracaoTelaGravar implements InteracaoTela {
		private GravadorDeEvento gravador = new GravadorDeEvento();
		private ReprodutorDeEvento reprodutor = new ReprodutorDeEvento();
		private TelaPrincipcal telaGravar = new TelaPrincipcal(this);
		
		{
			gravador.adicionarOuvinte(new OuvinteGravador() {
				
				@Override
				public void eventoGravado(EventoGravar eventoGravado) {
					if("tecla".equals(eventoGravado.tipoEvento) && "pressionado".equals(eventoGravado.evento)) {
						char codigo = (char) (Integer.valueOf(eventoGravado.tecla)).intValue();
						
						if(codigo == 120) 
							telaGravar.pausar();
					}
				}
			});
		}
		
		@Override
		public void gravar() {
			try {
				gravador.gravar();
			} catch (Exception e) {
				telaGravar.exibirMensagem("Problemas para gravar");
				e.printStackTrace();
			}			
		}

		@Override
		public void pausar() {
			try {
				gravador.pausar();
			} catch (Exception e) {
				telaGravar.exibirMensagem("Problemas para pausar a gravacao");
				e.printStackTrace();
			}
		}

		@Override
		public void parar() {
			File arquivo;
			while((arquivo = telaGravar.exibirSelecionadoDeArquivo()) == null);
			try {
				gravador.parar(arquivo);
			} catch (Exception e) {
				telaGravar.exibirMensagem("Problemas para parar a gravacao");
				e.printStackTrace();
			}
		}

		@Override
		public void reproduzir() {
			File arquivo = telaGravar.exibirSelecionadoDeArquivo();
			try {
				if(arquivo != null)
					reprodutor.reproduzir(arquivo);
			} catch (Exception e) {
				telaGravar.exibirMensagem("Problemas para reproduzir");
				e.printStackTrace();
			}
		}		
	};
	
	public void exibir() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new InteracaoTelaGravar().telaGravar.exibir();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

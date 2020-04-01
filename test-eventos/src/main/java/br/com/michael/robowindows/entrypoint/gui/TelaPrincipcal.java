package br.com.michael.robowindows.entrypoint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class TelaPrincipcal {

	static {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("LookAndFeel=disabled");
		}
	}

	public static interface InteracaoTela	{
		public void gravar();
		public void pausar();
		public void parar();
		public void reproduzir();
	}
	
	private JFrame frmGravador;
	private InteracaoTela interacaoTela;
	private boolean visibilidade = true;
	private JButton btnPausar = new JButton("");
	
	/**
	 * Create the application.
	 */
	public TelaPrincipcal(InteracaoTela ouvinteTela) {
		initialize();
		this.interacaoTela = ouvinteTela;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGravador = new JFrame();
		frmGravador.setResizable(false);
		frmGravador.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		frmGravador.setTitle("Robo Windows");
		frmGravador.setBounds(100, 100, 519, 150);
		frmGravador.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGravador.getContentPane().setLayout(null);
		
		JButton btnReproduzir = new JButton("");
		JButton btnParar = new JButton("");
		JButton btnGravar = new JButton("");
		
		btnReproduzir.setForeground(Color.BLACK);
		btnReproduzir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGravar.setEnabled(false);
				btnReproduzir.setEnabled(false);
				TelaPrincipcal.this.variarVisibilidade();
				interacaoTela.reproduzir();
				btnGravar.setEnabled(true);
				btnReproduzir.setEnabled(true);
				TelaPrincipcal.this.variarVisibilidade();
			}
		});
		btnReproduzir.setIcon(new ImageIcon(getClass().getResource("/imagens/play.png")));
		btnReproduzir.setBounds(0, 0, 120, 120);
		frmGravador.getContentPane().add(btnReproduzir, BorderLayout.WEST);
		
		
		btnPausar.setEnabled(false);
		btnPausar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGravar.setEnabled(true);
				btnPausar.setEnabled(false);
				btnParar.setEnabled(true);
				interacaoTela.pausar();
			}
		});
		btnPausar.setIcon(new ImageIcon(getClass().getResource("/imagens/pause.png")));
		btnPausar.setBounds(260, 0, 120, 120);
		frmGravador.getContentPane().add(btnPausar, BorderLayout.CENTER);
		
		btnParar.setEnabled(false);
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGravar.setEnabled(false);
				btnPausar.setEnabled(false);
				btnParar.setEnabled(false);
				interacaoTela.parar();
				btnGravar.setEnabled(true);
				btnPausar.setEnabled(false);
				btnParar.setEnabled(false);
				btnReproduzir.setEnabled(true);
			}
		});
		btnParar.setIcon(new ImageIcon(getClass().getResource("/imagens/stop.png")));
		btnParar.setBounds(390, 0, 120, 120);
		frmGravador.getContentPane().add(btnParar, BorderLayout.EAST);
		
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGravar.setEnabled(false);
				btnPausar.setEnabled(true);
				btnParar.setEnabled(true);
				btnReproduzir.setEnabled(false);
				TelaPrincipcal.this.variarVisibilidade();
				interacaoTela.gravar();
			}
		});
		btnGravar.setIcon(new ImageIcon(getClass().getResource("/imagens/record.png")));
		btnGravar.setBounds(130, 0, 120, 120);
		frmGravador.getContentPane().add(btnGravar);
	}
	
	public File exibirSelecionadoDeArquivo() {
		File arquivo = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath().concat(File.separator).concat("gravacao.json"));
		JFileChooser jfc = new JFileChooser(arquivo);
		
		jfc.setDialogTitle("Escolha um nome de arquivo");
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setSelectedFile(arquivo);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo JSON", "json");
		jfc.setFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue != JFileChooser.APPROVE_OPTION) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Selecione um arquivo valido");
			return null;
		}
		
		return jfc.getSelectedFile();
	}
	
	public void variarVisibilidade() {
		if(visibilidade)
			frmGravador.setExtendedState(JFrame.ICONIFIED);
		else
			frmGravador.setExtendedState(JFrame.NORMAL);
		
		visibilidade = !visibilidade;
	}
	
	public void exibirMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}

	public void pausar() {
		btnPausar.doClick();
		variarVisibilidade();
	}
	
	public void exibir() {
		frmGravador.setVisible(true);
	}
}

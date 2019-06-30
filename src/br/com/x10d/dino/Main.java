package br.com.x10d.dino;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends Canvas {
	
	public static final int WIDTH = 800, HEIGHT  = 700;
	
	public Jogo jogo;
	public KeyHandler keyHandler;
	private BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private Thread thread;
	
	private double dezesseisMilhoes = 1e9/60D; 
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		criaTela();
		
		thread = new Thread(() -> run());
		thread.start();
	}
	
	public void criaTela() {
		
		JFrame jFrame = new JFrame();
		setSize(WIDTH, HEIGHT);
		jFrame.setLayout(new BorderLayout());
		jFrame.setVisible(true);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel(new GridLayout(10, 1));
		JButton jButtonMelhorDinossauro = new JButton("Melhor dinossauro");
		jButtonMelhorDinossauro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				switch (actionEvent.getActionCommand()) {
					case "Melhor dinossauro":
						jogo.isMelhorDino = true;
						jButtonMelhorDinossauro.setText("Todos dinossauros");
						break;
						
					case "Todos dinossauros":
						jogo.isMelhorDino = false;
						jButtonMelhorDinossauro.setText("Melhor dinossauro");
						break;
				}
			}
		});
		
		JTextField jTextFieldTickSpeed = new JTextField();
		jTextFieldTickSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dezesseisMilhoes = 1e9/Integer.parseInt(jTextFieldTickSpeed.getText());
			}
		});
		
		jPanel.add(jButtonMelhorDinossauro);
		jPanel.add(jTextFieldTickSpeed);
		
		jFrame.add(jPanel, BorderLayout.EAST);
		jFrame.add(this, BorderLayout.CENTER);
		
		jFrame.pack();
		
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void iniciaJogoEHandler() {
		jogo = new Jogo();
		keyHandler = new KeyHandler(this);
	}
	
	public void run() {
		
		iniciaJogoEHandler();
		
		double ultimoAgoraEmNanoSegundos = System.nanoTime();
		
		double delta = 0;
		
		long agoraEmMillisegundos = System.currentTimeMillis();
		
		//int ticks = 0, frames = 0;
		
		while(true) {
			
			double agoraEmNanoSegundos = System.nanoTime();
			delta += (agoraEmNanoSegundos-ultimoAgoraEmNanoSegundos)/dezesseisMilhoes;
			ultimoAgoraEmNanoSegundos = agoraEmNanoSegundos;
			
			while(delta >= 1) {
				//ticks++;
				jogo.registraOcorrencias();
				delta--;
			}
			
			renderizaImagens();
			//frames++;
			
			if(System.currentTimeMillis() - agoraEmMillisegundos >= 1000) {
				agoraEmMillisegundos += 1000;
				//System.out.println("UPS: " + ticks + " FPS: " + frames);
				//frames = 0;
				//ticks = 0;
			}
		}
	}
	
	public void renderizaImagens() {
		
		BufferStrategy bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics graphicsTela = bufferStrategy.getDrawGraphics();
		
		graphicsTela.drawImage(bufferedImage, 0, 0, null);
		jogo.renderizaObjetos(graphicsTela);
		graphicsTela.dispose();
		bufferStrategy.show();
	}
}
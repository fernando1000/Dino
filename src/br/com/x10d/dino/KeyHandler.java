package br.com.x10d.dino;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private Main main;
	
	public KeyHandler(Main main) {
		this.main = main;
		main.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE || keyEvent.getKeyCode() == KeyEvent.VK_UP) main.jogo.listaComPopulacaoDeDinossauros.get(0).dinoPula();
		if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN) main.jogo.listaComPopulacaoDeDinossauros.get(0).dinoAbaixa(); 
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) main.jogo.listaComPopulacaoDeDinossauros.get(0).dinoEmPeh();
	}

	public void keyTyped(KeyEvent e) {
		
	}

}

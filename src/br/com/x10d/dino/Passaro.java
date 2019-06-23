package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Graphics;

public class Passaro extends Inimigo {
	
	private Coordenada vel = new Coordenada(1.1, 0);
	
	public Passaro(Coordenada pos) {
		this.largura = 15;
		this.altura = 15;
		this.coordenadasPosicao = pos; 
	}
	
	@Override
	public void marca() {
		coordenadasPosicao.adiciona(vel);
	}
	
	@Override
	public void renderiza(Graphics graphics, Coordenada coordenadaDiferenca) {
		
		graphics.setColor(Color.RED);
		graphics.fillRect(getXDaTela(coordenadaDiferenca), getYDaTela(coordenadaDiferenca), largura, altura);
	}
}

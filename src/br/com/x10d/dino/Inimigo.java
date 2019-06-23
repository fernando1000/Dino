package br.com.x10d.dino;

import java.awt.Graphics;

public abstract class Inimigo {

	protected int altura, largura;
	protected Coordenada coordenadasPosicao;
	
	public Coordenada getPosicao() {
		return coordenadasPosicao;
	}

	public abstract void marca();
	public abstract void renderiza(Graphics g, Coordenada offset);


	public int getXDaTela(Coordenada offset) {
		return coordenadasPosicao.getX() - offset.getX() + Main.WIDTH/2;
	}
	
	public int getYDaTela(Coordenada offset) {
		return coordenadasPosicao.getY() - offset.getY() + Main.HEIGHT/2-altura;
	}
}

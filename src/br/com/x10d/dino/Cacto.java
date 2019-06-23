package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Graphics;

public class Cacto extends Inimigo {
	
	public static final Coordenada coordenadaCactoMinusculo = new Coordenada(16,16);
	public static final Coordenada coordenadaCactoPequeno = new Coordenada(16,35);
	public static final Coordenada coordenadaCactoMedio = new Coordenada(33,35);
	public static final Coordenada coordenadaCactoGrande = new Coordenada(40,35); 
	
	public Cacto(Coordenada coordenada, Coordenada pos) {
		this.largura = coordenada.getX();
		this.altura = coordenada.getY();
		this.coordenadasPosicao = pos;
	}
	
	@Override
	public void renderiza(Graphics graphics, Coordenada offset) {
		
		graphics.setColor(Color.GREEN);
		graphics.fillRect(getXDaTela(offset), getYDaTela(offset), largura, altura);
	}

	@Override
	public void marca() {
		
	}
}
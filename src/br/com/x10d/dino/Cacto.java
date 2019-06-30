package br.com.x10d.dino;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;

public class Cacto extends Inimigo {
	
	public static final Coordenada coordenadaCactoMinusculo = new Coordenada(32,32);
	public static final Coordenada coordenadaCactoPequeno = new Coordenada(32,70);
	public static final Coordenada coordenadaCactoMedio = new Coordenada(66,70);
	public static final Coordenada coordenadaCactoGrande = new Coordenada(80,70); 
	
	public Cacto(Coordenada coordenada, Coordenada pos) {
		this.largura = coordenada.getX();
		this.altura = coordenada.getY();
		this.coordenadasPosicao = pos;
	}
	
	@Override
	public void renderiza(Graphics graphics, Coordenada coordenadaDiferenca) {
		
		URL resource = getClass().getResource("/imagens/cacto.png");
		ImageIcon img = new ImageIcon(resource);
		graphics.drawImage(img.getImage(), getXDaTela(coordenadaDiferenca), getYDaTela(coordenadaDiferenca), largura, altura, null);
		//graphics.setColor(Color.GREEN);
		//graphics.fillRect(getXDaTela(offset), getYDaTela(offset), largura, altura);
	}

	@Override
	public void marca() {
		
	}
}
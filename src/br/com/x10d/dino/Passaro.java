package br.com.x10d.dino;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;

public class Passaro extends Inimigo {
	
	private Coordenada coordenadaVelocidade = new Coordenada(1.1, 0);
	
	public Passaro(Coordenada pos) {
		this.largura = 30;
		this.altura = 30;
		this.coordenadasPosicao = pos; 
	}
	
	@Override
	public void marca() {
		coordenadasPosicao.adiciona(coordenadaVelocidade);
	}
	
	@Override
	public void renderiza(Graphics graphics, Coordenada coordenadaDiferenca) {
		
		URL resource = getClass().getResource("/imagens/aguia.png");
		ImageIcon img = new ImageIcon(resource);
		graphics.drawImage(img.getImage(), getXDaTela(coordenadaDiferenca), getYDaTela(coordenadaDiferenca), largura, altura, null);
		//graphics.setColor(Color.RED);
		//graphics.fillRect(getXDaTela(coordenadaDiferenca), getYDaTela(coordenadaDiferenca), largura, altura);
	}
}

package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;

public class Dinossauro {
	
	public static final Coordenada coordenadaGravidade = new Coordenada(0,1.2);
	private static final Coordenada coordenadaDinoEmPeh = new Coordenada(20, 40);
	private static final Coordenada coordenadaDinoAbaixado = new Coordenada(35, 20);
	private Coordenada coordenadaAreaOcupadaAtualmente = coordenadaDinoEmPeh;
	private Coordenada coordenadaPosicao, coordenadaVertical;
	public static final double puloVertical = -16;
	public boolean isDinoPulando = false;
	private boolean isDinoAbaixado = false;
	public int fitness;
	public RedeNeural redeNeural;
	
	public Dinossauro() {
		this.coordenadaPosicao = new Coordenada();
		this.coordenadaVertical = new Coordenada();
		redeNeural = new RedeNeural(6,4,2);
		redeNeural.aplicaAtivacoes(new int[2]);			
	}
	
	public Dinossauro(RedeNeural brain) {
		this.coordenadaPosicao = new Coordenada();
		this.coordenadaVertical = new Coordenada();
		this.redeNeural = brain;
	}
	
	public void moveForward(Coordenada coordenadaStVel, int tempoPontuacaoAtual) {
		fitness = tempoPontuacaoAtual;
		coordenadaPosicao.adiciona(coordenadaStVel);	
	}
	
	public void marca(double distancia, double velocidade, double dinoCoordenadaVertical, double alturaInimigo, double alturaDino, double isPulando) {
		
		double[] saidaResultadoNRA = redeNeural.calculaSaidaDaRedeNeural(new double[] {distancia/Main.WIDTH, velocidade/10, dinoCoordenadaVertical/10, alturaInimigo/Main.HEIGHT, alturaDino/Main.HEIGHT, isPulando});
		if(saidaResultadoNRA[0] > 0.5) dinoPula();
		if(saidaResultadoNRA[1] > 0.5) dinoAbaixa();
		
		coordenadaPosicao.adiciona(coordenadaVertical);
		
		if(coordenadaPosicao.getY() < 0) coordenadaVertical.adiciona(coordenadaGravidade);
		else {
			coordenadaPosicao.setY(0);
			if(coordenadaVertical.getY() > 0) coordenadaVertical.setY(0);
			isDinoPulando = false;
			isDinoAbaixado = false;
		}
	}
	
	public void dinoPula() {
		
		if(isDinoPulando) return;
		isDinoPulando = true;
		coordenadaVertical.setY(puloVertical);
	}
	
	public void dinoAbaixa() {
		
		if(coordenadaPosicao.getY() != 0 && !isDinoAbaixado) {
			if(!isDinoAbaixado) coordenadaVertical.adiciona(new Coordenada(0, 1));
			isDinoAbaixado = true;	
		}
		coordenadaAreaOcupadaAtualmente = coordenadaDinoAbaixado;
	}
	
	public void dinoEmPeh() {
		coordenadaAreaOcupadaAtualmente = coordenadaDinoEmPeh;
	}
	
	public void renderiza(Graphics graphics, Coordenada offset) {
		
		URL resource = getClass().getResource("/imagens/dinossauro.png");
		ImageIcon img = new ImageIcon(resource);
		graphics.drawImage(img.getImage(), 
						   coordenadaPosicao.getX()-offset.getX()+Main.WIDTH/2, 
						   coordenadaPosicao.getY()-offset.getY()+Main.HEIGHT/2-coordenadaAreaOcupadaAtualmente.getY(), 
						   coordenadaAreaOcupadaAtualmente.getX(), 
						   coordenadaAreaOcupadaAtualmente.getY(), 
						   null);

		//graphics.setColor(Color.CYAN);
		//graphics.fillRect(coordenadaPosicao.getX()-offset.getX()+Main.WIDTH/2, 
						  //coordenadaPosicao.getY()-offset.getY()+Main.HEIGHT/2-coordenadaAreaOcupadaAtualmente.getY(), 
						  //coordenadaAreaOcupadaAtualmente.getX(), 
						  //coordenadaAreaOcupadaAtualmente.getY());
	}
	
	public Coordenada getPosicao() {
		return coordenadaPosicao;
	}
	
	public Coordenada getCoordenadaVertical() {
		return coordenadaVertical;
	}
	
	public Coordenada getCoordenadaAreaOcupada() {
		return coordenadaAreaOcupadaAtualmente;
	}
}
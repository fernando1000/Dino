package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Graphics;

public class Dinossauro {
	
	public static final Coordenada coordenadaGravidade = new Coordenada(0,1.2);
	public static final double jumpVel = -16;
	
	private static final Coordenada coordenadaDinoEmPeh = new Coordenada(20, 40);
	private static final Coordenada coordenadaDinoAbaixado = new Coordenada(35, 20);
	
	private Coordenada coordenadaPosicao, vel;
	private Coordenada coordenadaAreaOcupadaAtualmente = coordenadaDinoEmPeh;
	public boolean isPulando = false;
	private boolean isEmbaixo = false;
	
	public int fitness;
	
	public RedeNeural redeNeural;
	
	public Dinossauro() {
		this.coordenadaPosicao = new Coordenada();
		this.vel = new Coordenada();
		redeNeural = new RedeNeural(6,4,2);
		redeNeural.aplicaAtivacoes(new int[2]);			
	}
	
	public Dinossauro(RedeNeural brain) {
		this.coordenadaPosicao = new Coordenada();
		this.vel = new Coordenada();
		this.redeNeural = brain;
	}
	
	public void moveForward(Coordenada stVel, int timer) {
		fitness = timer;
		coordenadaPosicao.adiciona(stVel);	
	}
	
	public void tick(double distance, double speed, double yvel, double entityheight, double height, double isJumping) {
		
		double[] output = redeNeural.calculaSaidaDaRedeNeural(new double[] {distance/Main.WIDTH, speed/10, yvel/10, entityheight/Main.HEIGHT, height/Main.HEIGHT, isJumping});
		if(output[0] > 0.5) jump();
		if(output[1] > 0.5) dinoAbaixado();
		
		coordenadaPosicao.adiciona(vel);
		
		if(coordenadaPosicao.getY() < 0) vel.adiciona(coordenadaGravidade);
		else {
			coordenadaPosicao.setY(0);
			if(vel.getY() > 0) vel.setY(0);
			isPulando = false;
			isEmbaixo = false;
		}
	}
	
	public void jump() {
		if(isPulando) return;
		isPulando = true;
		vel.setY(jumpVel);
	}
	
	public void dinoAbaixado() {
		
		if(coordenadaPosicao.getY() != 0 && !isEmbaixo) {
			if(!isEmbaixo) vel.adiciona(new Coordenada(0, 1));
			isEmbaixo = true;	
		}
		coordenadaAreaOcupadaAtualmente = coordenadaDinoAbaixado;
	}
	
	public void dinoEmPeh() {
		coordenadaAreaOcupadaAtualmente = coordenadaDinoEmPeh;
	}
	
	public void renderiza(Graphics graphics, Coordenada offset) {
		
		graphics.setColor(Color.CYAN);
		graphics.fillRect(coordenadaPosicao.getX()-offset.getX()+Main.WIDTH/2, coordenadaPosicao.getY()-offset.getY()+Main.HEIGHT/2-coordenadaAreaOcupadaAtualmente.getY(), coordenadaAreaOcupadaAtualmente.getX(), coordenadaAreaOcupadaAtualmente.getY());
	}
	
	public Coordenada getPosicao() {
		return coordenadaPosicao;
	}
	
	public Coordenada getVel() {
		return vel;
	}
	
	public Coordenada getCoordenadaAreaOcupada() {
		return coordenadaAreaOcupadaAtualmente;
	}
}
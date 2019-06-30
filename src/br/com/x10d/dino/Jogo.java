package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class Jogo {
	
	public ArrayList<Inimigo> listaComInimigos = new ArrayList<Inimigo>();
	public ArrayList<Dinossauro> listaComPopulacaoDeDinossauros = new ArrayList<Dinossauro>();
	public ArrayList<Dinossauro> listaComProximaPopulacaoDeDinossauros = new ArrayList<Dinossauro>();
	
	private Random random = new Random();
	
	private Coordenada coordenadasDiferenca;
	private static Coordenada coordenadasVelocidade = new Coordenada(5, 0);
	
	private int melhorFitness = 0; 
	private int geracaoAtual = 1;
	protected boolean isMelhorDino = false;
	private static final int QUANTIDADE_POPULACAO = 1000;
	private SomDoJogo somDoJogo;
	
	public Jogo() {
		
		somDoJogo = new SomDoJogo();
		
		for(int i = 0; i < QUANTIDADE_POPULACAO; i++) {
			listaComPopulacaoDeDinossauros.add(new Dinossauro());			
		}
		
		coordenadasDiferenca = new Coordenada(listaComPopulacaoDeDinossauros.get(0).getPosicao().getX(), 0);
	}
	
	int tempoInimigo = 0;
	int tempoPontuacaoAtual = 0;
	
	public void registraOcorrencias() {
		
		if(listaComPopulacaoDeDinossauros.size()==0) repopular();
		tempoInimigo++;
		
		for(Dinossauro dinossauro : listaComPopulacaoDeDinossauros) {
			
			dinossauro.moveForward(coordenadasVelocidade, tempoPontuacaoAtual);
			
			double distancia = 0;
			double alturaInimigo = 0;
			
			for(Inimigo inimigo : listaComInimigos) {
				
				if(inimigo.getPosicao().getX() > dinossauro.getPosicao().getX()) {
					
					distancia = inimigo.getPosicao().x - dinossauro.getPosicao().x;
					alturaInimigo = inimigo.getPosicao().y;
					break;
				}
			}
			double velocidade = coordenadasVelocidade.x;
			
			dinossauro.processaEntradas(distancia, 
										velocidade, 
										dinossauro.getCoordenadaVertical().y, 
										-alturaInimigo, 
										-dinossauro.getPosicao().y, 
										dinossauro.isDinoPulando?1:0);
		}
		
		for(Inimigo inimigo : listaComInimigos) {
			inimigo.marca();
		}
		
		tempoPontuacaoAtual += coordenadasVelocidade.getX();
		
		if(tempoPontuacaoAtual % 1000 == 0) coordenadasVelocidade.adiciona(new Coordenada(1,0));
		
		verificaColisao();
		
		removeInimigos();
		
		adicionaCactosOuPassaros();
		
		atualizaDiferencas();
	}
	
	public void verificaColisao() {
		
		Iterator<Dinossauro> dinossauroIterator = listaComPopulacaoDeDinossauros.iterator();
		
		while(dinossauroIterator.hasNext()) {
			
			Dinossauro dinossauro = dinossauroIterator.next();
			for(Inimigo inimigo : listaComInimigos) {
				
				if(houveColisao(dinossauro, inimigo)) {
					
					if(listaComPopulacaoDeDinossauros.size() == 1) {
						somDoJogo.tocaAudio("/sons/colisao.wav");
					}
					
					listaComProximaPopulacaoDeDinossauros.add(dinossauro);
					dinossauroIterator.remove();
					break;
				}
			}
		}
	}
	
	public void removeInimigos() {
		
		Iterator<Inimigo> inimigoIterator = listaComInimigos.iterator();
		
		while(inimigoIterator.hasNext()) {
			
			Inimigo inimigo = inimigoIterator.next();
			if(inimigo.getXDaTela(coordenadasDiferenca) < -inimigo.largura) {
				inimigoIterator.remove();
			}
		}
	}
	
	public void atualizaDiferencas() {
		if(listaComPopulacaoDeDinossauros.size() > 0) {
			coordenadasDiferenca = new Coordenada(listaComPopulacaoDeDinossauros.get(0).getPosicao().getX(), 0);
		}
	}
	
	private double rangeDePassaros = 0.2;
	private int tamanhoMinimo = 50, tamanhoMaximo = 130;
	private int qtdCriacaoPassaro = 5000;
	
	public void adicionaCactosOuPassaros() {
		
		if(tempoInimigo > (random.nextInt(tamanhoMaximo-tamanhoMinimo)+tamanhoMinimo) && random.nextDouble() > 0.8) {
			
			if(tempoPontuacaoAtual < qtdCriacaoPassaro) adicionaCacto();
			else {
				if(random.nextDouble() < rangeDePassaros) adicionaPassaro();
				else adicionaCacto();
			}
			tempoInimigo = 0;
		}
	}

	private void repopular() {
		
		geracaoAtual++;
		
		listaComProximaPopulacaoDeDinossauros.sort(new Comparator<Dinossauro>() {
			public int compare(Dinossauro d1, Dinossauro d2) {
				return d2.fitness-d1.fitness;
			}
		});

		for(Dinossauro dinossauro : listaComProximaPopulacaoDeDinossauros) {
			
			if(dinossauro.fitness > melhorFitness) melhorFitness = dinossauro.fitness;
		}
		
		
		for(Dinossauro dinossauro : listaComProximaPopulacaoDeDinossauros) {
			
			listaComPopulacaoDeDinossauros.add(new Dinossauro(dinossauro.redeNeural.clone()));
//			population.add(new Dinosaur(d.brain.clone().mutate(0.05, 0.8)));
			listaComPopulacaoDeDinossauros.add(new Dinossauro());
			if(listaComPopulacaoDeDinossauros.size()==listaComProximaPopulacaoDeDinossauros.size()) break;
		}
		
		listaComInimigos.clear();
		coordenadasVelocidade.set(5, 0);
		listaComProximaPopulacaoDeDinossauros.clear();
		tempoPontuacaoAtual = 0;
		tempoInimigo = 0;
	}
	
	private void adicionaPassaro() {
		
		somDoJogo.tocaAudio("/sons/Passaro.wav");

		int alturaPassaro = random.nextInt(3);
		
		switch (alturaPassaro) {
			case 0:
				listaComInimigos.add(new Passaro(new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2, 0)));
				break;
			case 1:
				listaComInimigos.add(new Passaro(new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2, -25)));
				break;
			case 2:
				listaComInimigos.add(new Passaro(new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2, -65)));
				break;
		}
		
	}

	public void adicionaCacto() {
		
		int alturaCacto = random.nextInt(4);
		
		switch(alturaCacto) {
			case 0:
				listaComInimigos.add(new Cacto(Cacto.coordenadaCactoMinusculo, new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2, 0)));
				break;
			case 1:
				listaComInimigos.add(new Cacto(Cacto.coordenadaCactoPequeno, new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2,0)));
				break;
			case 2:
				listaComInimigos.add(new Cacto(Cacto.coordenadaCactoGrande, new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2,0)));
				break;
			case 3:
				listaComInimigos.add(new Cacto(Cacto.coordenadaCactoMedio, new Coordenada(tempoPontuacaoAtual+Main.WIDTH/2,0)));
				break;
		}
	}
	
	public void renderizaObjetos(Graphics graphicsTela) {
		
		graphicsTela.setColor(Color.LIGHT_GRAY);
		graphicsTela.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		graphicsTela.setColor(Color.BLACK);
		graphicsTela.drawLine(0, Main.HEIGHT/2, Main.WIDTH, Main.HEIGHT/2);
		
		for(Inimigo inimigo : listaComInimigos) {
			inimigo.renderiza(graphicsTela, coordenadasDiferenca);
		}
		
		if(!isMelhorDino)
		for(Dinossauro dinossauro : listaComPopulacaoDeDinossauros) {
			dinossauro.renderiza(graphicsTela, coordenadasDiferenca);
		}
		else if(listaComPopulacaoDeDinossauros.size()>0)listaComPopulacaoDeDinossauros.get(0).renderiza(graphicsTela, coordenadasDiferenca);
		graphicsTela.setColor(Color.DARK_GRAY);
		graphicsTela.drawString("Melhor pontuacao: " + melhorFitness, 600, 100);
		graphicsTela.drawString("Dinossauros restantes: " + listaComPopulacaoDeDinossauros.size(), 600, 120);
		graphicsTela.drawString("Geracao Atual: " + geracaoAtual, 600, 140);
		graphicsTela.drawString("Pontuacao Atual: " + tempoPontuacaoAtual, 600, 160);
		
		if(listaComPopulacaoDeDinossauros.size()>0)
		listaComPopulacaoDeDinossauros.get(0).redeNeural.renderizaRedeNeural(graphicsTela, 50, 50, 250, 250);
	}
	
	public boolean houveColisao(Dinossauro dinossauro, Inimigo inimigo) {
		
		if(dinossauro.getPosicao().getX() + dinossauro.getCoordenadaAreaOcupada().getX() >= inimigo.getPosicao().getX() && 
				(inimigo.getPosicao().getX()+inimigo.largura) >= dinossauro.getPosicao().getX() &&
					dinossauro.getPosicao().getY() <= inimigo.getPosicao().getY() && 
						(inimigo.getPosicao().getY() - inimigo.altura) <= dinossauro.getPosicao().getY())
			return true;
		return false;
	}
}

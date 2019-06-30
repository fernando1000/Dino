package br.com.x10d.dino;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RedeNeural implements Cloneable {

	public double[][] neuronios;
	public double[][][] pesos;
	public double[][] bias;
	public FuncaoDeAtivacao[] funcaoDeAtivacao;
	public FuncaoDeCusto FUNCAO_DE_CUSTO = FuncaoDeCusto.QUADRATIC;
	public static double lambda = 0.1;
	public int correct = 0;
	public int total = 0;
	
	//private double custoMaximo = 0;
	
	public ArrayList<Double> custoOverTime = new ArrayList<Double>();
	
	public RedeNeural(int...layerSizes) {
		
		iniciaRede(layerSizes);
		randomIniciais();
	}
	
	public void aplicaAtivacoes(int...id) {
		
		if(id.length != pesos.length) throw new RuntimeException("tamanho da funcao de ativacao esta invalido");
		
		this.funcaoDeAtivacao = new FuncaoDeAtivacao[id.length];
		
		for(int i = 0; i < id.length;i++) {
			for(FuncaoDeAtivacao funcaoDeAtivacao : FuncaoDeAtivacao.values()) {
				
				if(funcaoDeAtivacao.getId() == id[i]) {
					this.funcaoDeAtivacao[i] = funcaoDeAtivacao;
					break;
				}
			}
			
			//throw new RuntimeException("Could not find activation id");
		}
	}
	
	private void randomIniciais() {
		
		Random r = new Random();
		for(int n = 0; n < pesos.length; n++) {
			for (int i = 0; i < pesos[n].length; i++)
				for (int j = 0; j < pesos[n][i].length; j++) {
					pesos[n][i][j] = r.nextGaussian()*0.2;					
				}
		}
	}

	private void iniciaRede(int...l) {
		
		neuronios = new double[l.length][];
		for(int j = 0; j < neuronios.length; j++) {
			neuronios[j] = new double[l[j]];
		}

		pesos = new double[neuronios.length-1][][];
		for(int j = 0; j < pesos.length; j++) {
			pesos[j] = new double[l[j+1]][l[j]];
		}
		
		bias = new double[pesos.length][];
		for(int j = 0; j < bias.length; j++) {
			bias[j] = new double[l[j+1]];
		}
//		Arrays.stream(l).forEach(i -> System.out.print(i+"×"));
	}
		
	public double[] calculaSaidaDaRedeNeural(double[] entradas) {
		
		double[][] qtdNeuronios = new double[neuronios.length-1][];
		
		this.neuronios[0] = entradas;
		
		for(int i = 0; i < qtdNeuronios.length; i++) {
			
			qtdNeuronios[i] = adiciona(bias[i],matrix(neuronios[i], pesos[i]));
			
			neuronios[i+1] = funcaoAtivacao(qtdNeuronios[i],funcaoDeAtivacao[i]);
		}
		return neuronios[qtdNeuronios.length];
	}
	
	private double[] adiciona(double a[], double[] b) {
		
		double[] result = new double[b.length];
		Arrays.setAll(result, i -> a[i]+b[i]);
		return result;
	}

	private double[] matrix(double[] neuronios, double[][] pesos) {
		
		double[] tamanhoPesos = new double[pesos.length];
		
		for (int i = 0; i < tamanhoPesos.length; i++) {
			
			for (int j = 0; j < neuronios.length; j++) {
				
				tamanhoPesos[i] += neuronios[j] * pesos[i][j];
			}
		}
		return tamanhoPesos;
	}
	
	private double[] funcaoAtivacao(double[] layer, FuncaoDeAtivacao funcaoDeAtivacao) {
		
		double[] output = new double[layer.length];
		switch(funcaoDeAtivacao) {
		case SIGMOID:
			for (int i = 0; i < layer.length; i++) {
				output[i] = 1 / (1 + Math.exp(-layer[i]));
			}
			break;
		case TANH:
			for (int i = 0; i < layer.length; i++) {
				double ex = Math.exp(-2*layer[i]);
				output[i] = 2/(1+ex)-1;
			}
			break;
		case RELU:
			for (int i = 0; i < layer.length; i++) {
				output[i] = Math.max(0, layer[i]);				
			}
		}
		return output;
	}

	public RedeNeural clone() {
		
		try {
			return (RedeNeural) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void renderizaRedeNeural(Graphics graphics, int areaX, int areaY, int largura, int altura) {
		
		int diametro = 15;
		int espacoY = 7;
		int espacoX = 100;
		int maximoDeNeuronios = 10;
		
		int[] yoa = new int[neuronios.length];
		for(int n = 0; n < yoa.length; n++) {
			yoa[n] = neuronios[n].length > maximoDeNeuronios ? (altura-maximoDeNeuronios*(diametro+espacoY))/2:(altura-neuronios[n].length*(diametro+espacoY))/2;
		}
		graphics.setFont(new Font("Arial", Font.BOLD, 9));
		FontMetrics fontm = graphics.getFontMetrics();
		graphics.setColor(new Color(0xb0, 0xc0, 0x20));
		graphics.fillRect(areaX-10, areaY-10, largura, altura);
		
		//Pesos
		for(int n = 0; n < pesos.length; n++) {
			for(int i = 0; i < Math.min(maximoDeNeuronios,pesos[n][0].length); i++) {
				for(int j = 0; j < Math.min(maximoDeNeuronios,pesos[n].length); j++) {
					double weight = pesos[n][j][i];
					graphics.setColor(weight>0 ? new Color(0, 0, 0xff, (int) Math.min(0xff,0xff*weight)):new Color(0xff, 0, 0, (int) (Math.min(0xff,0xff*-weight))));
					graphics.drawLine(espacoX*n+areaX+diametro/2, areaY+yoa[n]+i*(diametro+espacoY)+diametro/2, areaX+espacoX*(n+1)+diametro/2, areaY+ yoa[n+1]+j*(espacoY+diametro)+diametro/2);
				}
			}
		}
		
		//Biases
		for(int n = 0; n < bias.length; n++) {
			for(int i = 0; i < Math.min(maximoDeNeuronios,bias[n].length); i++) {
				double wb = bias[n][i];
				graphics.setColor(wb>0 ? new Color(0, 0xff, 0, (int) Math.min(0xff,0xff*wb)):new Color(0xff, 0x70, 0, (int) (Math.min(0xff,0xff*-wb))));
				graphics.drawLine(espacoX*n+areaX+diametro/2, areaY+altura-yoa[n]+diametro/2, espacoX*(n+1)+areaX+diametro/2, areaY+yoa[n+1]+i*(diametro+espacoY)+diametro/2);
			}				
		}
		
		//Neuronios
		for(int n = 0; n < neuronios.length; n++) {
			for(int i = 0; i < Math.min(maximoDeNeuronios,neuronios[n].length); i++) {
				double activation = neuronios[n][i];
				int col = (int) Math.min(0xff,Math.abs(0xff*activation));
				graphics.setColor(new Color(col,col,col));
				int y = yoa[n]+i*(diametro+espacoY);
				graphics.fillOval(espacoX*n+areaX, y+areaY, diametro, diametro);
				graphics.setColor(activation>0.8 ? Color.black:Color.white);
				String o = new DecimalFormat("#.##").format(activation)+"";
				graphics.drawString(o, 
						espacoX*n+areaX+(diametro-fontm.stringWidth(o))/2, 
						areaY+y+(diametro-fontm.getHeight())/2+fontm.getAscent());
			}
		}
		
		for(int n = 0; n < bias.length; n++) {
			graphics.setColor(Color.white);
			graphics.fillOval(espacoX*n+areaX, areaY+altura-yoa[n], diametro, diametro);
		}
	}
	
}
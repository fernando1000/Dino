package br.com.x10d.dino;

public enum FuncaoDeAtivacao {
	SIGMOID(0), TANH(1), RELU(2);
	
	private int id;
	
	FuncaoDeAtivacao(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
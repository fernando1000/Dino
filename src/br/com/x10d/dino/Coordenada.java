package br.com.x10d.dino;

public class Coordenada {

	public double x, y;

	public Coordenada(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordenada() {
		this(0, 0);
	}

	public Coordenada adiciona(Coordenada v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Coordenada subtrai(Coordenada v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	public Coordenada multiplica(double s) {
		this.x *= s;
		this.y *= s;
		return this;
	}

	public Coordenada divide(double s) {
		this.x /= s;
		this.y /= s;
		return this;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void set(int x, int y) {
		setX(x);
		setY(y);
	}
}

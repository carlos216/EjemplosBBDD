package es.upm.dit.adsw.ejemplosbbdd;

public class Planeta {
	private long id;
	private String nombre;
	private String name;
	private double radio;
	private double distancia;
	private double dia;
	private double anno;
	private double temperatura;
	private double gravedad;

	public Planeta(long id, String nombre, String name, double radio,
			double distancia, double dia, double anno, double temperatura,
			double gravedad) {
		this.id = id;
		this.nombre = nombre;
		this.name = name;
		this.radio = radio;
		this.distancia = distancia;
		this.dia = dia;
		this.anno = anno;
		this.temperatura = temperatura;
		this.gravedad = gravedad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public double getDia() {
		return dia;
	}

	public void setDia(double dia) {
		this.dia = dia;
	}

	public double getAnno() {
		return anno;
	}

	public void setAnno(double anno) {
		this.anno = anno;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getGravedad() {
		return gravedad;
	}

	public void setGravedad(double gravedad) {
		this.gravedad = gravedad;
	}

	@Override
	public String toString() {
		return nombre;
	}
}

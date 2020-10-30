package motogpAPI;

public class RiderStandingsData {
	String nombre;
	int posicion;
	double puntosLiga;

	public RiderStandingsData(int posicion, String nombre, double puntosLiga) {
		this.nombre = nombre;
		this.posicion = posicion;
		this.puntosLiga = puntosLiga;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPosicion() {
		return posicion;
	}

	public double getPuntosLiga() {
		return puntosLiga;
	}

	@Override
	public String toString() {
		return "RiderStandingsData{" +
				"posicion=" + posicion +
				", nombre='" + nombre + '\'' +
				", puntosLiga=" + puntosLiga +
				'}';
	}
}

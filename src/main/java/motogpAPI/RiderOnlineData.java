package motogpAPI;

public class RiderOnlineData {
	private int dorsal;
	private String nombre;
	private String nacionalidad;
	private String equipo;
	private int posicion;
	private int tiempo;
	private int vueltas;

	public RiderOnlineData(int posicion, int dorsal, String nombre, String nacionalidad, String equipo, int tiempo, int vueltas) {
		this.dorsal = dorsal;
		this.nombre = nombre;
		this.equipo = equipo;
		this.posicion = posicion;
		this.nacionalidad = nacionalidad;
		this.tiempo = tiempo;
		this.vueltas = vueltas;
	}

	public int getTiempo() {
		return tiempo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public int getDorsal() {
		return dorsal;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEquipo() {
		return equipo;
	}

	public int getPosicion() {
		return posicion;
	}

	public int getVueltas() {
		return vueltas;
	}

	@Override
	public String toString() {
		return "RiderOnlineData{" +
				"posicion=" + posicion +
				", dorsal=" + dorsal +
				", nombre='" + nombre + '\'' +
				", nacionalidad='" + nacionalidad + '\'' +
				", equipo='" + equipo + '\'' +
				", tiempo=" + tiempo +
				", vueltas=" + vueltas +
				'}';
	}
}
package motogpAPI.model;

import motogpAPI.Category;

public class InfoCarrera {
	
	String nombreEvento;
	Integer posicion;
	Integer puntos;
	Integer numeros;
	String piloto;
	String pais;
	String equipo;
	String raceCode;
	Category category;
	String lugar;
	String fecha;
	
	Double kmh;
	Integer vueltaMasRapidaPole;
	public InfoCarrera(String nombreEvento, Integer posicion, Integer puntos, Integer numeros, String piloto,
			String pais, String equipo, Double kmh, Integer vueltaMasRapidaPole,String raceCode,Category category,String lugar,	String fecha
) {
		super();
		this.nombreEvento = nombreEvento;
		this.posicion = posicion;
		this.puntos = puntos;
		this.numeros = numeros;
		this.piloto = piloto;
		this.pais = pais;
		this.equipo = equipo;
		this.kmh = kmh;
		this.vueltaMasRapidaPole = vueltaMasRapidaPole;
		this.category=category;
		this.lugar=lugar;
		this.fecha=fecha;
		this.raceCode=raceCode;
	}
	public String getNombreEvento() {
		return nombreEvento;
	}
	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	public Integer getPosicion() {
		return posicion;
	}
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public Integer getNumeros() {
		return numeros;
	}
	public void setNumeros(Integer numeros) {
		this.numeros = numeros;
	}
	public String getPiloto() {
		return piloto;
	}
	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public String getRaceCode() {
		return raceCode;
	}
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Double getKmh() {
		return kmh;
	}
	public void setKmh(Double kmh) {
		this.kmh = kmh;
	}
	public Integer getVueltaMasRapidaPole() {
		return vueltaMasRapidaPole;
	}
	public void setVueltaMasRapidaPole(Integer vueltaMasRapidaPole) {
		this.vueltaMasRapidaPole = vueltaMasRapidaPole;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((kmh == null) ? 0 : kmh.hashCode());
		result = prime * result + ((lugar == null) ? 0 : lugar.hashCode());
		result = prime * result + ((nombreEvento == null) ? 0 : nombreEvento.hashCode());
		result = prime * result + ((numeros == null) ? 0 : numeros.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((piloto == null) ? 0 : piloto.hashCode());
		result = prime * result + ((posicion == null) ? 0 : posicion.hashCode());
		result = prime * result + ((puntos == null) ? 0 : puntos.hashCode());
		result = prime * result + ((raceCode == null) ? 0 : raceCode.hashCode());
		result = prime * result + ((vueltaMasRapidaPole == null) ? 0 : vueltaMasRapidaPole.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoCarrera other = (InfoCarrera) obj;
		if (category != other.category)
			return false;
		if (equipo == null) {
			if (other.equipo != null)
				return false;
		} else if (!equipo.equals(other.equipo))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (kmh == null) {
			if (other.kmh != null)
				return false;
		} else if (!kmh.equals(other.kmh))
			return false;
		if (lugar == null) {
			if (other.lugar != null)
				return false;
		} else if (!lugar.equals(other.lugar))
			return false;
		if (nombreEvento == null) {
			if (other.nombreEvento != null)
				return false;
		} else if (!nombreEvento.equals(other.nombreEvento))
			return false;
		if (numeros == null) {
			if (other.numeros != null)
				return false;
		} else if (!numeros.equals(other.numeros))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (piloto == null) {
			if (other.piloto != null)
				return false;
		} else if (!piloto.equals(other.piloto))
			return false;
		if (posicion == null) {
			if (other.posicion != null)
				return false;
		} else if (!posicion.equals(other.posicion))
			return false;
		if (puntos == null) {
			if (other.puntos != null)
				return false;
		} else if (!puntos.equals(other.puntos))
			return false;
		if (raceCode == null) {
			if (other.raceCode != null)
				return false;
		} else if (!raceCode.equals(other.raceCode))
			return false;
		if (vueltaMasRapidaPole == null) {
			if (other.vueltaMasRapidaPole != null)
				return false;
		} else if (!vueltaMasRapidaPole.equals(other.vueltaMasRapidaPole))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InfoCarrera [nombreEvento=" + nombreEvento + ", posicion=" + posicion + ", puntos=" + puntos
				+ ", numeros=" + numeros + ", piloto=" + piloto + ", pais=" + pais + ", equipo=" + equipo
				+ ", raceCode=" + raceCode + ", category=" + category + ", lugar=" + lugar + ", fecha=" + fecha
				+ ", kmh=" + kmh + ", vueltaMasRapidaPole=" + vueltaMasRapidaPole + "]";
	}
	
	
}
package org.springframework.samples.dreamgp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recordVueltaRapida")
public class RecordVueltaRapida extends BaseEntity {

	public Integer vuelta;
	public String nombrePiloto;
	public Integer tiempo;
	public Double kmh;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="vueltaRapida")
    private Record record;

	public RecordVueltaRapida() {
		super();
	}
	
	public RecordVueltaRapida(String entrada) {

		String atributos = entrada.split(":")[2].trim();
		String[] split = atributos.split(" ");
		Integer x = split.length;
		if (x < 4) {
			this.nombrePiloto = null;
			this.tiempo = null;
			this.kmh = null;
			this.vuelta = null;
		} else {
			try {
				this.vuelta = Integer.parseInt(split[0]);
				this.nombrePiloto = split[1];
				this.kmh = Double.parseDouble(split[x-1]);
				
				for (int i=2; i < x-2; i++) {
					this.nombrePiloto += " " + split[i];
				}
				
				String[] tiempoSplit = split[x-2].split("'");
				this.tiempo = (int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000);
			} catch (Exception e) {

			}
		}
	}

	public RecordVueltaRapida(Integer vuelta, String nombrePiloto, Integer tiempo, Double kmh) {
		super();
		this.vuelta = vuelta;
		this.nombrePiloto = nombrePiloto;
		this.tiempo = tiempo;
		this.kmh = kmh;
	}

	public Integer getVuelta() {
		return vuelta;
	}

	public void setVuelta(Integer vuelta) {
		this.vuelta = vuelta;
	}

	public String getNombrePiloto() {
		return nombrePiloto;
	}

	public void setNombrePiloto(String nombrePiloto) {
		this.nombrePiloto = nombrePiloto;
	}

	public Integer getTiempo() {
		return tiempo;
	}

	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}

	public Double getKmh() {
		return kmh;
	}

	public void setKmh(Double kmh) {
		this.kmh = kmh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kmh == null) ? 0 : kmh.hashCode());
		result = prime * result + ((nombrePiloto == null) ? 0 : nombrePiloto.hashCode());
		result = prime * result + ((tiempo == null) ? 0 : tiempo.hashCode());
		result = prime * result + ((vuelta == null) ? 0 : vuelta.hashCode());
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
		RecordVueltaRapida other = (RecordVueltaRapida) obj;
		if (kmh == null) {
			if (other.kmh != null)
				return false;
		} else if (!kmh.equals(other.kmh))
			return false;
		if (nombrePiloto == null) {
			if (other.nombrePiloto != null)
				return false;
		} else if (!nombrePiloto.equals(other.nombrePiloto))
			return false;
		if (tiempo == null) {
			if (other.tiempo != null)
				return false;
		} else if (!tiempo.equals(other.tiempo))
			return false;
		if (vuelta == null) {
			if (other.vuelta != null)
				return false;
		} else if (!vuelta.equals(other.vuelta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecordVueltaRapida [vuelta=" + vuelta + ", nombrePiloto=" + nombrePiloto + ", tiempo=" + tiempo
				+ ", kmh=" + kmh + "]";
	}

}

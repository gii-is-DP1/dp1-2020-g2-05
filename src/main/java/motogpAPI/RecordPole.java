package motogpAPI;

public class RecordPole {

	public String nombrePiloto;
	public Integer tiempo;
	public Double kmh;

	public RecordPole(String entrada) {
		super();
		String atributos = entrada.split(":")[1].trim();
		String[] split = atributos.split(" ");
		Integer x = split.length;
		if (x < 4) {
			this.nombrePiloto = null;
			this.tiempo = null;
			this.kmh = null;
		} else {
			this.nombrePiloto = split[0];
			for (int i=1; i < x-2; i++) {
				this.nombrePiloto += " " + split[i];
			}
			String[] tiempoSplit = split[x-2].split("'");
			this.tiempo = (int) ((Integer.parseInt(tiempoSplit[0]) * 60 + Double.parseDouble(tiempoSplit[1]) + 1e-14) * 1000);
			this.kmh = Double.parseDouble(split[x-1]);
		}
	}

	public RecordPole(String nombrePiloto, Integer tiempo, Double kmh) {
		super();
		this.nombrePiloto = nombrePiloto;
		this.tiempo = tiempo;
		this.kmh = kmh;
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
		RecordPole other = (RecordPole) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "RecordPole [nombrePiloto=" + nombrePiloto + ", tiempo=" + tiempo + ", kmh=" + kmh + "]";
	}

}

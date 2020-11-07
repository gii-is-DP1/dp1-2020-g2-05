package motogpAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

public class Record {

	public RecordPole vueltaDePole;
	public RecordVueltaRapida vueltaRapida;
	public RecordCircuito recordDelCircuito;
	public RecordMejorVuelta mejorVuelta;

	public Record(String entrada) throws IOException {
		super();

		String html = Jsoup.connect(entrada).get().text();
		if (!html.contains("Récords: ") ) {
			this.vueltaDePole = null;
			this.vueltaRapida = null;
			this.recordDelCircuito = null;
			this.mejorVuelta = null;
		} else {
			String records = html.split("Récords: ")[1];
			String[] prueba = records.split("Km/h");
			List<String> listaRecords = new ArrayList<String>();
			for (int i=0; i < prueba.length; i++) {
				listaRecords.add(prueba[i].trim());
			}

			this.vueltaDePole = new RecordPole(prueba[0]);
			this.vueltaRapida = new RecordVueltaRapida(prueba[1]);
			this.recordDelCircuito = new RecordCircuito(prueba[2]);
			this.mejorVuelta = new RecordMejorVuelta(prueba[3]);
		}
	}

	public Record(RecordPole vueltaDePole, RecordVueltaRapida vueltaRapida, RecordCircuito recordDelCircuito,
			RecordMejorVuelta mejorVuelta) {
		super();
		this.vueltaDePole = vueltaDePole;
		this.vueltaRapida = vueltaRapida;
		this.recordDelCircuito = recordDelCircuito;
		this.mejorVuelta = mejorVuelta;
	}


	public RecordPole getVueltaDePole() {
		return vueltaDePole;
	}


	public void setVueltaDePole(RecordPole vueltaDePole) {
		this.vueltaDePole = vueltaDePole;
	}


	public RecordVueltaRapida getVueltaRapida() {
		return vueltaRapida;
	}


	public void setVueltaRapida(RecordVueltaRapida vueltaRapida) {
		this.vueltaRapida = vueltaRapida;
	}


	public RecordCircuito getRecordDelCircuito() {
		return recordDelCircuito;
	}


	public void setRecordDelCircuito(RecordCircuito recordDelCircuito) {
		this.recordDelCircuito = recordDelCircuito;
	}


	public RecordMejorVuelta getMejorVuelta() {
		return mejorVuelta;
	}


	public void setMejorVuelta(RecordMejorVuelta mejorVuelta) {
		this.mejorVuelta = mejorVuelta;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mejorVuelta == null) ? 0 : mejorVuelta.hashCode());
		result = prime * result + ((recordDelCircuito == null) ? 0 : recordDelCircuito.hashCode());
		result = prime * result + ((vueltaDePole == null) ? 0 : vueltaDePole.hashCode());
		result = prime * result + ((vueltaRapida == null) ? 0 : vueltaRapida.hashCode());
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
		Record other = (Record) obj;
		if (mejorVuelta == null) {
			if (other.mejorVuelta != null)
				return false;
		} else if (!mejorVuelta.equals(other.mejorVuelta))
			return false;
		if (recordDelCircuito == null) {
			if (other.recordDelCircuito != null)
				return false;
		} else if (!recordDelCircuito.equals(other.recordDelCircuito))
			return false;
		if (vueltaDePole == null) {
			if (other.vueltaDePole != null)
				return false;
		} else if (!vueltaDePole.equals(other.vueltaDePole))
			return false;
		if (vueltaRapida == null) {
			if (other.vueltaRapida != null)
				return false;
		} else if (!vueltaRapida.equals(other.vueltaRapida))
			return false;
		return true;
	}


	@Override
	public String toString() {
		if (vueltaDePole == null || vueltaRapida == null || recordDelCircuito == null || mejorVuelta == null) {
			return "No disponemos de datos para la fecha, el pais y la categoria introducidos";
		} else {
			return "Record [vueltaDePole=" + vueltaDePole + ", vueltaRapida=" + vueltaRapida + ", recordDelCircuito="
					+ recordDelCircuito + ", mejorVuelta=" + mejorVuelta + "]";
		}
	}

}

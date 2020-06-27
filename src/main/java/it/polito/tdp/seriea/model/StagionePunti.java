package it.polito.tdp.seriea.model;

public class StagionePunti implements Comparable<StagionePunti>{
	
	private Integer stagione;
	private Integer punti;
	
	public StagionePunti(Integer stagione, Integer punti) {
		this.stagione = stagione;
		this.punti = punti;
	}

	public Integer getStagione() {
		return stagione;
	}

	public void setStagione(Integer stagione) {
		this.stagione = stagione;
	}

	public Integer getPunti() {
		return punti;
	}

	public void setPunti(Integer punti) {
		this.punti = punti;
	}
	
	public void incrementaPunti(Integer i) {
		this.punti += i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stagione == null) ? 0 : stagione.hashCode());
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
		StagionePunti other = (StagionePunti) obj;
		if (stagione == null) {
			if (other.stagione != null)
				return false;
		} else if (!stagione.equals(other.stagione))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return stagione + ", " + punti;
	}

	@Override
	public int compareTo(StagionePunti o) {
		return this.getStagione()-o.getStagione();
	}
}

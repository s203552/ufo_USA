package it.polito.tdp.ufo.bean;

public class CoppiaInt {

	private int anno;
	private int Navvistamenti;
	
	public CoppiaInt(int anno, int navvistamenti) {
		super();
		this.anno = anno;
		Navvistamenti = navvistamenti;
	}
	@Override
	public String toString() {
		return "CoppiaInt [anno=" + anno + ", Navvistamenti=" + Navvistamenti + "]";
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getNavvistamenti() {
		return Navvistamenti;
	}
	public void setNavvistamenti(int navvistamenti) {
		Navvistamenti = navvistamenti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
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
		CoppiaInt other = (CoppiaInt) obj;
		if (anno != other.anno)
			return false;
		return true;
	}
	
	
}

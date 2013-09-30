package com.lloguer.clases;

public class Lloguer {
	
	private long id;
	private String soci;
	private String fianca;
	private String cobrat;
	private String datall;
	private String datad;
	private String material;
	
	public String getCobrat() {
		return cobrat;
	}
	public void setCobrat(String cobrat) {
		this.cobrat = cobrat;
	}
	public String getDatall() {
		return datall;
	}
	public void setDatall(String datall) {
		this.datall = datall;
	}
	public String getDatad() {
		return datad;
	}
	public void setDatad(String datad) {
		this.datad = datad;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSoci() {
		return soci;
	}
	public void setSoci(String soci) {
		this.soci = soci;
	}
	public String getFianca() {
		return fianca;
	}
	public void setFianca(String fianca) {
		this.fianca = fianca;
	}
	
	public void cursorAIdea(){
		
	}

	// Este método será invocado en el ArrayAdapter del ListView
		@Override
		public String toString() {
			
			String iconoImportancia = "";
			/*
			if (importancia == 0) // Alta
				iconoImportancia = "100€";
			else if (importancia == 1)
				iconoImportancia = "50$";
			else if (importancia==2)
				iconoImportancia ="1£";
			*/
			return "Soci:" + soci 
					+"\nFiança:" +fianca
					+"\nCobrat:" +cobrat
					+"\nData Lloguer:" +datall
					+"\nData Retorn:" +datad
					+"\nMaterial:" +material;
		}
}
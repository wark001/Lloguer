package com.lloguer.clases;

public class Lloguer {
	
	private long id;
	private String soci;
	private String fianca;
	private String cobrat;
	private String datall;
	private String datad;
	private String material;
	private int tipus_activitat;
	/*private int tornat;
	
	public int getTornat() {
		return tornat;
	}
	public void setTornat(int tornat) {
		this.tornat = tornat;
	}*/
	public int getTipus_activitat() {
		return tipus_activitat;
	}
	public void setTipus_activitat(int tipus_activitat) {
		this.tipus_activitat = tipus_activitat;
	}
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
			
			String tipusActivitat = "";
			
			if (tipus_activitat == 0) // Alta
				tipusActivitat = "Ent. Exp. Entrenament expedicions, no es cobra lloguer.";
			else if (tipus_activitat == 1)
				tipusActivitat = "CMM Activitats programades pel C.M.M., no es cobra lloguer.";
			else if (tipus_activitat==2)
				tipusActivitat ="Part. Es cobra lloguer";
			
			return "Soci:" + soci 
					+"\nTipus Activitat:" +tipusActivitat
					+"\nFiança:" +fianca
					+"\nCobrat:" +cobrat
					+"\nData Lloguer:" +datall
					+"\nData Retorn:" +datad
					+"\nMaterial:" +material;
		}
}
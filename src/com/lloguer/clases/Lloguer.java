package com.lloguer.clases;

public class Lloguer {
	
	private long id;
	private Soci idSoci;
	private Material idMaterial;
	private String tipusActivitat;
	private String fianca;
	private String cobrat;
	private String dataLloguer;
	private String dataTornat;
	
	public Lloguer(Soci idSoci,Material idMaterial,String tipusActivitat,String fianca,String cobrat,String dataLloguer,String dataTornat){
		
		this.idSoci=idSoci;
		this.idMaterial=idMaterial;
		this.tipusActivitat=tipusActivitat;
		this.fianca=fianca;
		this.cobrat=cobrat;
		this.dataLloguer=dataLloguer;
		this.dataTornat=dataTornat;
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Soci getIdSoci() {
		return idSoci;
	}
	public void setIdSoci(Soci idSoci) {
		this.idSoci = idSoci;
	}
	public Material getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Material idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getTipusActivitat() {
		return tipusActivitat;
	}
	public void setTipusActivitat(String tipusActivitat) {
		this.tipusActivitat = tipusActivitat;
	}
	public String getFianca() {
		return fianca;
	}
	public void setFianca(String fianca) {
		this.fianca = fianca;
	}
	public String getCobrat() {
		return cobrat;
	}
	public void setCobrat(String cobrat) {
		this.cobrat = cobrat;
	}
	public String getDataLloguer() {
		return dataLloguer;
	}
	public void setDataLloguer(String dataLloguer) {
		this.dataLloguer = dataLloguer;
	}
	public String getDataTornat() {
		return dataTornat;
	}
	public void setDataTornat(String dataTornat) {
		this.dataTornat = dataTornat;
	}

	
}
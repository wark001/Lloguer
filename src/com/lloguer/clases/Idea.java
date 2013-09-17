package com.lloguer.clases;

public class Idea {
	private long id;
	private String tituloIdea;
	private String textoIdea;
	private int importancia;
	
	//Marc
	private String fecha;
	private String mail;
	private String telefono;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTituloIdea() {
		return tituloIdea;
	}

	public void setTituloIdea(String tituloIdea) {
		this.tituloIdea = tituloIdea;
	}
	
	public String getTextoIdea() {
		return textoIdea;
	}

	public void setTextoIdea(String idea) {
		this.textoIdea = idea;
	}

	public int getImportancia() {
		return importancia;
	}

	public void setImportancia(int importancia) {
		this.importancia = importancia;
	}
	
	// Este método será invocado en el ArrayAdapter del ListView
	@Override
	public String toString() {
		
		String iconoImportancia = "";
		
		if (importancia == 0) // Alta
			iconoImportancia = "100€";
		else if (importancia == 1)
			iconoImportancia = "50$";
		else if (importancia==2)
			iconoImportancia ="1£";
		
		return "N:" + tituloIdea 
				+"\nA:" +textoIdea
				+"\n€:" +iconoImportancia
				+"\nF:" +fecha
				+"\n@:" +mail
				+"\nT:" +telefono;
	}
}
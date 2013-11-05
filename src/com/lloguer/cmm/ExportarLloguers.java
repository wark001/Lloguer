package com.lloguer.cmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.widget.Toast;

public class ExportarLloguers {

	final String DIRECTORI="/CMMLloguer";
	final String NOMBRE_ARCHIVO="llistat_Lloguer.cvs";

	private boolean mAlmacenamientoExternoDisponible = false;
	private boolean mAlmacenamientoExternoEscritura = false;
	
	public ExportarLloguers(){
		
	}
	
	//1 preparar directori
	public void crearDirectory(){
		String path=Environment.getExternalStorageDirectory().getAbsolutePath()+DIRECTORI;
	    File llocArxiu = new File(path);
	    llocArxiu.mkdirs();
	}
	//2 llegir contingut
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	         //   listFilesForFolder(folder);   
	        } else {
	        	//llegeix del sql, consulta
	        //	llegeix(fileEntry);
	        }
	    }
	}
	//3 guardar contingut
	private void guardarEnAlmacenExterno(String[] contingutArxius,String[] nomArxius) {

			//if (contingutArxius.length>1){
				//Toast.makeText(this, getString(R.string.rellena_campos), Toast.LENGTH_LONG).show();
			//}else {
				checkEstadoAlmacenExterno();
				if (mAlmacenamientoExternoDisponible && mAlmacenamientoExternoEscritura) {
					
					try {
						
						// Se crea la ruta para almacenar el archivo en la ra�z del
						// almacenamiento externo (desde donde la apilcaci�n tiene permisos)
						String path=Environment.getExternalStorageDirectory().getAbsolutePath()+
								DIRECTORI;
						
						File llocArxiu = new File(path);
						llocArxiu.mkdirs();
						File file = new File(llocArxiu, NOMBRE_ARCHIVO);
						
						FileWriter fichero = new FileWriter(file);
				        PrintWriter pw = new PrintWriter(fichero);
			            for (int i = 0; nomArxius[i]!=null; i++){
			                pw.println(nomArxius[i]+" - "+contingutArxius[i]);
			            }
			            fichero.close();
		
						//Toast.makeText(this, getString("Arxiu Guarda"), Toast.LENGTH_LONG).show();
						//result.setText("Resultat a: "+path+ Html.fromHtml("<br />"));
						
					} catch (FileNotFoundException e) {
						//Toast.makeText(this, getString(R.string.error_fnf), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					} catch (IOException e) {
						//Toast.makeText(this, getString(R.string.error_io), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				
			//}
	}
	
	//auxiliar per verificar si hi ha acc�s als directoris
	private void checkEstadoAlmacenExterno() {
		String estadoAlmacenExterno = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(estadoAlmacenExterno)) {
		    // Se puede leer y escribir
		    mAlmacenamientoExternoDisponible = mAlmacenamientoExternoEscritura = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(estadoAlmacenExterno)) {
		    // S�lo se puede escribir
		    mAlmacenamientoExternoDisponible = true;
		    mAlmacenamientoExternoEscritura = false;
		} else {
		    // No se puede leer ni escribir. 
			// La variable "estadoAlmacenExterno" dar� la informaci�n exacata. 
		    mAlmacenamientoExternoDisponible = mAlmacenamientoExternoEscritura = false;
		}
	}
	
}

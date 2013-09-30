package com.lloguer.clases;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IdeasDataSource {

	// Campos relacionados con la base de datos
	private SQLiteDatabase baseDeDatos;
	private IdeasDatabaseHelper dbHelper;
	private String[] todasLasColumnas = { 
			IdeasDatabaseHelper.COLUMN_ID,
			IdeasDatabaseHelper.COLUMN_SOCI, 
			IdeasDatabaseHelper.COLUMN_FIANCA, 

			};

	// Constructor que inicializa el DAO, 
	// Inicializa la conexi�n con la base de datos, que est� encapsulada en el Helper
	public IdeasDataSource(Context context) {
		dbHelper = new IdeasDatabaseHelper(context);
	}

	// Crea (en caso de que a�n no exista) o abre la base de datos
	public void open() throws SQLException {
		baseDeDatos = dbHelper.getWritableDatabase();
	}

	// Cierra la conexi�n con la base de datos
	public void close() {
		dbHelper.close();
	}

	// M�todo que insertar� una Idea en base de datos
	public Lloguer createIdea(String etsoci, String etfianca ) {
		
		// Esta clase se usa para almacenar un conjunto de valores que el ContentResolver podr� procesar. 
		ContentValues valores = new ContentValues();
		valores.put(IdeasDatabaseHelper.COLUMN_SOCI, etsoci);
		valores.put(IdeasDatabaseHelper.COLUMN_FIANCA, etfianca);

		
		// Inserci�n en base de datos. El m�todo insert() devolver� la primary key (columna _id)
		// que se autogenera al insertar.
		// El m�todo insertOrThrow() es igual que insert() pero lanza una SQLException en caso de
		// no poder insertar
		long idInsertado = baseDeDatos.insertOrThrow(IdeasDatabaseHelper.TABLE_IDEAS, null, valores);
		
		// Una vez insertada la nueva Idea, se extrae de base de datos a trav�s del cursor
		// para que este m�todo devuelva el bean completo
		Cursor cursor = baseDeDatos.query(IdeasDatabaseHelper.TABLE_IDEAS,
				todasLasColumnas, IdeasDatabaseHelper.COLUMN_ID + " = " + idInsertado, null, null, null, null);
		
		cursor.moveToFirst();		
		Lloguer nouLloguer = cursorAIdea(cursor);
		
		// Es importante cerrar el cursor para evitar consumir memoria innecesaria
		cursor.close();		
		return nouLloguer;
	}

	public void deleteIdea(Lloguer lloguer) {
		// Se extrae el identificador (primary key o PK) para localizar la idea a borrar
		// gracias a la cl�usla where
		long idIdea = lloguer.getId();		
		
		// Se borra la idea de la base de datos
		baseDeDatos.delete(IdeasDatabaseHelper.TABLE_IDEAS, IdeasDatabaseHelper.COLUMN_ID + " = " + idIdea, null);
		
		Log.i(IdeasDataSource.class.getName(), "Idea con id: " + idIdea + " borrada.");
	}
	
	public void updateIdea(Lloguer lloguer) {
		// Se extrae la PK para localizar la idea a actualizar 
		// y se crea un ContentValues con los nuevos valores de la idea
		long idIdea = lloguer.getId();		
		ContentValues valores = new ContentValues();
	/*	valores.put(IdeasDatabaseHelper.COLUMN_TITULO_IDEA, lloguer.getTituloIdea());
		valores.put(IdeasDatabaseHelper.COLUMN_TEXTO_IDEA, lloguer.getTextoIdea());
		valores.put(IdeasDatabaseHelper.COLUMN_IMPORTANCIA, lloguer.getImportancia());
		
		//Marc
				valores.put(IdeasDatabaseHelper.COLUMN_FECHA, lloguer.getFecha());
				valores.put(IdeasDatabaseHelper.COLUMN_MAIL, lloguer.getFecha());
				valores.put(IdeasDatabaseHelper.COLUMN_TELEFONO, lloguer.getFecha());
	*/			
		baseDeDatos.update(IdeasDatabaseHelper.TABLE_IDEAS, valores, IdeasDatabaseHelper.COLUMN_ID + " = " + idIdea, null);

		Log.i(IdeasDataSource.class.getName(), "Idea con id: " + idIdea + " actualizada correctamente.");
	}
	
	public Lloguer getIdea(long idIdea) {
		// Se busca una Idea a trav�s de su id						
		Cursor cursor = baseDeDatos.query(IdeasDatabaseHelper.TABLE_IDEAS,
				todasLasColumnas, IdeasDatabaseHelper.COLUMN_ID + " = " + idIdea, null, null, null, null);
		
		if (cursor.getCount() == 1) {
		
			cursor.moveToFirst();		
			Lloguer lloguer = cursorAIdea(cursor);
			
			// Es importante cerrar el cursor para evitar consumir memoria innecesaria
			cursor.close();
	
			Log.i(IdeasDataSource.class.getName(), "Idea con id: " + idIdea + " obtenida correctamente.");
			return lloguer;
		}
		else {
			
			Log.w(IdeasDataSource.class.getName(), "ATENCI�N. No se ha encontrado Idea con id: " + idIdea);
			return null;
		}
	}

	public List<Lloguer> getAllIdeas() {
		// Lista de Ideas
		List<Lloguer> lloguers = new ArrayList<Lloguer>();

		// Consulta a la base de datos
		// El no a�adir argumentos salvo la tabla y las columnas a obtener
		// har� que se lance la query "select * from ideas;"
		Cursor cursor = baseDeDatos.query(IdeasDatabaseHelper.TABLE_IDEAS,
				todasLasColumnas, null, null, null, null, null);

		// Se mueve el cursor al primer resultado 
		cursor.moveToFirst();
		
		// Se recorre el cursor para ir llenando el List<Idea>
		while (!cursor.isAfterLast()) {
			Lloguer lloguer = cursorAIdea(cursor);
			lloguers.add(lloguer);
			cursor.moveToNext();
		}
		
		// Es importante cerrar el cursor para evitar consumir memoria innecesaria
		cursor.close();
		return lloguers;
	}

	// M�todo auxiliar que permite convertir cursor (fila de la tabla ideas) en un objeto Idea
	private Lloguer cursorAIdea(Cursor cursor) {
		
		// El orden de los campos del cursor se corresponde con el 
		// orden de las columnas en la sentencia "create" (DATABASE_CREATE) 
		// de IdeasDatabaseHelper
		
		Lloguer lloguer = new Lloguer();
		lloguer.setId(cursor.getLong(0));
		lloguer.setSoci(cursor.getString(1));
		lloguer.setFianca(cursor.getString(2));
	/*	lloguer.setImportancia(cursor.getInt(3));
		//Marc
		lloguer.setFecha(cursor.getString(4));
		lloguer.setMail(cursor.getString(5));
		lloguer.setTelefono(cursor.getString(6));
	*/	return lloguer;
	}
}

package com.lloguer.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IdeasDatabaseHelper extends SQLiteOpenHelper {

	// Nombre y versi�n de la base de datos (ser� tambi�n el nombre del archivo que se generar� en el dispositivo)
	private static final String DATABASE_NAME = "lloguer_material.db";
	private static final int DATABASE_VERSION = 2;
	
	public static final String TABLE_IDEAS = "ideas";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SOCI = "nom_soci";
	public static final String COLUMN_FIANCA = "fianca_material";
	public static final String COLUMN_COBRAT = "cobrat";
	public static final String COLUMN_DATALL = "data_lloguer";
	public static final String COLUMN_DATAD = "data_devolucio";
	public static final String COLUMN_MATERIAL = "material";
	

	// Sentencia para la creaci�n de la base de datos (crea la tabla)
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_IDEAS + "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_SOCI + " text not null, " 
			+ COLUMN_FIANCA  + " text not null, "
			+ COLUMN_COBRAT + " text not null, " 
			+ COLUMN_DATALL + " text not null, " 
			+ COLUMN_DATAD + " text not null, " 
			+ COLUMN_MATERIAL + " text not null);";

	// Constructor que deber� invocar a super()
	public IdeasDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Se han de sobrescribir los m�todos onCreate() y onUpdate()
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Se lanza la sentencia SQL para la creaci�n de la tabla, sobre el objeto "database"
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(IdeasDatabaseHelper.class.getName(),
				"Actualizando la base de datos desde la versi�n " + oldVersion + " a la "
						+ newVersion + ". Se eliminar�n todos los datos antiguos.");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDEAS);
		onCreate(db);
	}
}

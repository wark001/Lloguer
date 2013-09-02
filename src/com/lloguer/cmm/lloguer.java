package com.lloguer.cmm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class lloguer extends Activity {

	Calendar dataLloguer = Calendar.getInstance();
	Calendar dataRetorn = Calendar.getInstance();
	
	//variables del layout
	TextView tvSoci,tvActivitat,tvFianca,tvCobrat,tvLloguer,tvRetorn,tvMaterial;
	EditText etSoci,etActivitat,etFianca,etCobrat,etLloguer,etRetorn,etMaterial;
	Button btAddMaterial,btMaterialBack ,btLloguer ,btCancel;
	Spinner spActivitat;
	LinearLayout afegirMaterial;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lloguer);
        
        //crida de les variables que fem servir
        spActivitat=(Spinner) findViewById(R.id.spActivitat);
        etLloguer=(EditText) findViewById(R.id.etLloguer);
        tvRetorn=(EditText) findViewById(R.id.tvRetorn);
        LinearLayout afegirMaterial = (LinearLayout) findViewById(R.id.afegirMaterial);
        
        //
    	//Creació del Spinner de tipus d'activitat
    	//
    	ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.tipusActivitat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActivitat.setAdapter(adapter);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    //
    //funcions que realitzen la entrada d'una data al camps de lloguer
    //
    OnDateSetListener dataDialogLloguer =new OnDateSetListener (){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			dataLloguer.set(Calendar.YEAR, year);
			dataLloguer.set(Calendar.MONTH, monthOfYear);
			dataLloguer.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			actualizarDataLloguer();		
		}
	};
	public void fucnioDataLloguer(View v) {
		new DatePickerDialog(lloguer.this, dataDialogLloguer, dataLloguer.get(Calendar.YEAR), dataLloguer.get(Calendar.MONTH), dataLloguer.get(Calendar.DAY_OF_MONTH)).show();
	}
	private void actualizarDataLloguer() {
		DateFormat formatoFecha = DateFormat.getDateInstance();
		etLloguer.setText(formatoFecha.format((dataLloguer.getTime())));	
	}
	
	//
    //funcions que realitzen la entrada d'una data al camps de retorn de lloguer
    //
	public void fucnioDataRetorn(View v) {
		new DatePickerDialog(lloguer.this, dataDialogRetorn, dataRetorn.get(Calendar.YEAR), dataRetorn.get(Calendar.MONTH), dataRetorn.get(Calendar.DAY_OF_MONTH)).show();
	}
	OnDateSetListener dataDialogRetorn =new OnDateSetListener (){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			dataRetorn.set(Calendar.YEAR, year);
			dataRetorn.set(Calendar.MONTH, monthOfYear);
			dataRetorn.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			actualizarDataRetorn();		
		}
	};
	private void actualizarDataRetorn() {
		DateFormat formatoFecha = DateFormat.getDateInstance();
		etRetorn.setText(formatoFecha.format((dataLloguer.getTime())));	
	}
	
	//
	//Funcions bottons
	//
	//borra data d'arribada
	private void borrarDataRetorn() {
		etRetorn.setText("");	
	}
	
	//afegeix la data a la qual es va tornar un material i no permet modifica
	private void dataUnMaterial(){
		etRetorn.setText(etRetorn.getText().toString()+" Material tornat el: "+getFechaActual());
	}
	//Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }
	
    private void realitza_lloguer(){
    
    	//cridar sql
    	//cridar activitat llista
    	
    }
    
    
/*	private void afegeixElements(){
		
		LinearLayout myLayout = new LinearLayout(this);
		EditText myEditText = new EditText(this);
		Button myButton = new Button(this);
		Button myButton2 = new Button(this);
		
		myButton.setText("Press Me");
		
		myLayout.addView(myEditText);
		myLayout.addView(myButton);
		myLayout.addView(myButton2);
		
		afegirMaterial.addView(myLayout);
			
	}*/
	
	
	/*
	 * <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >
          
	    <TextView
        android:id="@+id/tvMaterial"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Material"
        android:textAppearance="?android:attr/textAppearanceLarge" />

	    <EditText
	        android:id="@+id/etMaterial"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:layout_weight="1"
	        android:ems="10">
	        <requestFocus />
	    </EditText>
	    
	    <Button
	        android:id="@+id/btAddMaterial"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="Afegir un altre Material" 
	        android:onClick="afegeixElements"/>
	    <Button
	        android:id="@+id/btMaterialBack"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="Tornat" 
	        android:onClick="dataUnMaterial"/>
	     
    </LinearLayout>
	 */
	
	
	
	
}

package com.lloguer.cmm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
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
import android.widget.Toast;

import com.lloguer.clases.Lloguer;
import com.lloguer.clases.IdeasDataSource;

public class funcionsLloguer extends Activity {

	Calendar dataLloguer = Calendar.getInstance();
	Calendar dataRetorn = Calendar.getInstance();
	
	//variables del layout
	TextView tvSoci,tvActivitat,tvFianca,tvCobrat,tvLloguer,tvRetorn,tvMaterial;
	EditText etSoci,etActivitat,etFianca,etCobrat,etLloguer,etRetorn,etMaterial;
	Button btAddMaterial,btMaterialBack ,btLloguer ,btCancel;
	Spinner spActivitat;
	LinearLayout afegirMaterial;
	
	//afegit
	public final static String MODO = "MODO";
	public final static String ID_IDEA = "_ID";	
	public final static int MODO_NUEVA_IDEA = 0;
	public final static int MODO_EDITAR_IDEA = 1;
	private IdeasDataSource ideasDataSource;
	private int MODO_ACTUAL = 0;
	private Intent intentIdeasActivity = null;
	// Variable para "recordar" el identificador de la Idea que se está editando 
	private long idIdea;
	
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
        
        //afegit
        intentIdeasActivity = new Intent(this, llistat.class);
		intentIdeasActivity.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		
		Bundle bundle = getIntent().getExtras();        
        MODO_ACTUAL = bundle.getInt(MODO);
        
        ideasDataSource = new IdeasDataSource(this);
		ideasDataSource.open();
		
        if (MODO_ACTUAL == MODO_EDITAR_IDEA)        		
        	cargarIdea(bundle.getLong(ID_IDEA));
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
		new DatePickerDialog(funcionsLloguer.this, dataDialogLloguer, dataLloguer.get(Calendar.YEAR), dataLloguer.get(Calendar.MONTH), dataLloguer.get(Calendar.DAY_OF_MONTH)).show();
	}
	private void actualizarDataLloguer() {
		DateFormat formatoFecha = DateFormat.getDateInstance();
		etLloguer.setText(formatoFecha.format((dataLloguer.getTime())));	
	}
	
	//
    //funcions que realitzen la entrada d'una data al camps de retorn de lloguer
    //
	public void fucnioDataRetorn(View v) {
		new DatePickerDialog(funcionsLloguer.this, dataDialogRetorn, dataRetorn.get(Calendar.YEAR), dataRetorn.get(Calendar.MONTH), dataRetorn.get(Calendar.DAY_OF_MONTH)).show();
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
    
    
    @Override
	protected void onResume() {
		ideasDataSource.open();	
		super.onResume();
	}

	@Override
	protected void onPause() {
		ideasDataSource.close();
		super.onPause();
	}
	
	private void cargarIdea(long idIdea) {
		// Se obtiene la idea a editar
		Lloguer lloguer = ideasDataSource.getIdea(idIdea);
		this.idIdea = lloguer.getId();
		
		// Se vuelca su información en el formulario
	/*	((EditText) findViewById(R.id.tituloIdea)).setText(idea.getTituloIdea());
		((EditText) findViewById(R.id.textoIdea)).setText(idea.getTextoIdea());		
		((Spinner) findViewById(R.id.spinnerImportanciaIdea)).setSelection(idea.getImportancia());
		((EditText) findViewById(R.id.editText1)).setText(idea.getFecha());
		((EditText) findViewById(R.id.editText2)).setText(idea.getMail());
		((EditText) findViewById(R.id.editText3)).setText(idea.getTelefono());
		*/
	}
    /*
     public void guardar(View view) {
		
		Idea idea = new Idea();
		
		String tituloIdea = ((EditText) findViewById(R.id.tituloIdea)).getText().toString();
		String textoIdea = ((EditText) findViewById(R.id.textoIdea)).getText().toString();
		String editText1 = ((EditText) findViewById(R.id.editText1)).getText().toString();
		String editText2 = ((EditText) findViewById(R.id.editText2)).getText().toString();
		String editText3 = ((EditText) findViewById(R.id.editText3)).getText().toString();
		int importancia = ((Spinner) findViewById(R.id.spinnerImportanciaIdea)).getSelectedItemPosition();
		
		
		if (tituloIdea.equals("") || 
				textoIdea.equals("") || 
				editText1.equals("") || 
				editText2.equals("") || 
				editText3.equals("") )
			Toast.makeText(this, getString(R.string.rellena_campos_idea), Toast.LENGTH_LONG).show();
		else {
			
			idea.setId(idIdea);
			idea.setTituloIdea(tituloIdea);
			idea.setTextoIdea(textoIdea);
			idea.setImportancia(importancia);
			
			//Marc
			idea.setFecha(editText1);
			idea.setMail(editText2);
			idea.setTelefono(editText3);
			
			switch (MODO_ACTUAL) {
			case MODO_NUEVA_IDEA:
				ideasDataSource.createIdea(tituloIdea, textoIdea, importancia,editText1,editText2,editText3);
				break;
			case MODO_EDITAR_IDEA:
				ideasDataSource.updateIdea(idea);
				break;
			default:
				break;
			}			
			
			// Se vuelve a la actividad anterior, sin invocar a una nueva instancia de la misma.
			// (Otra opción sería invocar a finish(), ya que esta actividad ya no se 
			// utilizará hasta que se vuelva a solicitar desde la lista de ideas, aunque sería 
			// menos eficiente si se consultan muchas ideas)			
			startActivity(intentIdeasActivity);
		}
	}
     */
}
   
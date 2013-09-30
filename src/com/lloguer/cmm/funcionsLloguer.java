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

	Calendar dataLloguer = Calendar.getInstance();  //lloguer
	Calendar dataRetorn = Calendar.getInstance(); //retorn
	
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
        etRetorn=(EditText) findViewById(R.id.etRetorn);
        etMaterial=(EditText) findViewById(R.id.etMaterial);
        // bariables a guardar
        etFianca = (EditText) findViewById(R.id.etFianca);
        etSoci = (EditText) findViewById(R.id.etSoci);
        etCobrat = (EditText) findViewById(R.id.etCobrat);
        etLloguer = (EditText) findViewById(R.id.etLloguer);
        etRetorn=(EditText) findViewById(R.id.etRetorn);
        etMaterial=(EditText) findViewById(R.id.etMaterial);
        
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
		
        if (MODO_ACTUAL == MODO_EDITAR_IDEA){        		
        	cargarIdea(bundle.getLong(ID_IDEA));
        }
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
    public void fucnioDataLloguer(View v) {
		new DatePickerDialog(funcionsLloguer.this, dataDialogLloguer, dataLloguer.get(Calendar.YEAR), 
				dataLloguer.get(Calendar.MONTH), dataLloguer.get(Calendar.DAY_OF_MONTH)).show();
	}
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
	
	private void actualizarDataLloguer() {
		DateFormat formatoFecha = DateFormat.getDateInstance();
		etLloguer.setText(formatoFecha.format((dataLloguer.getTime())));	
	}
	
	//
    //funcions que realitzen la entrada d'una data al camps de retorn de lloguer
    //
	public void fucnioDataRetorn(View v) {
		new DatePickerDialog(funcionsLloguer.this, dataDialogRetorn, dataRetorn.get(Calendar.YEAR), 
				dataRetorn.get(Calendar.MONTH), dataRetorn.get(Calendar.DAY_OF_MONTH)).show();
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
		etRetorn.setText(formatoFecha.format((dataRetorn.getTime())));	
	}
	
	//
	//Funcions bottons
	//
	//borra data de retorn
	public void borrarDataRetorn(View v) {
		etRetorn.setText("");	
	}
	
	//afegeix la data a la qual es va tornar un material i no permet modifica
	public void dataUnMaterial(View v){
		//etMaterial
		etMaterial.setText(etMaterial.getText().toString()+" Material tornat el: "+getFechaActual().toString());
	}
	//Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }
	
    public void realitza_lloguer(View view){
    	guardar();	
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
	
	public void backToList(View v){
		this.finish();
	}
	
	public void guardar() {
		
		Lloguer intanciaLloguer = new Lloguer();
        
		if (etSoci.getText().toString().equals("") || 
				etFianca.getText().toString().equals(""))
			Toast.makeText(this, getString(R.string.rellena_campos_idea), Toast.LENGTH_LONG).show();
		else {
			
			intanciaLloguer.setSoci(etSoci.getText().toString());//idea
			intanciaLloguer.setFianca(etFianca.getText().toString());//idea
			intanciaLloguer.setCobrat(etCobrat.getText().toString());//idea
			intanciaLloguer.setDatall(etLloguer.getText().toString());//idea
			intanciaLloguer.setDatad(etRetorn.getText().toString());//idea
			intanciaLloguer.setMaterial(etMaterial.getText().toString());//idea
			
			switch (MODO_ACTUAL) {
			case MODO_NUEVA_IDEA:
				ideasDataSource.createIdea(
						etSoci.getText().toString(), 
						etFianca.getText().toString(),
						etCobrat.getText().toString(),
						etLloguer.getText().toString(),
						etRetorn.getText().toString(),
						etMaterial.getText().toString());
				break;
			case MODO_EDITAR_IDEA:
				ideasDataSource.updateIdea(intanciaLloguer);
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
	
	public void cargarIdea(long idIdea) {
		// Se obtiene la idea a editar
		Lloguer lloguer = ideasDataSource.getIdea(idIdea);
		this.idIdea = lloguer.getId();
		
		// Se vuelca su información en el formulario
	//	etFianca = (EditText) findViewById(R.id.etFianca);
        etSoci.setText(lloguer.getSoci());
		etFianca.setText(lloguer.getFianca());
		etCobrat.setText(lloguer.getCobrat());
		etLloguer.setText(lloguer.getDatall());
		etRetorn.setText(lloguer.getDatad());
		etMaterial.setText(lloguer.getMaterial());
	//	((EditText) findViewById(R.id.textoIdea)).setText(idea.getTextoIdea());		
	/*	((Spinner) findViewById(R.id.spinnerImportanciaIdea)).setSelection(idea.getImportancia());
		((EditText) findViewById(R.id.editText1)).setText(idea.getFecha());
		((EditText) findViewById(R.id.editText2)).setText(idea.getMail());
		((EditText) findViewById(R.id.editText3)).setText(idea.getTelefono());
		*/
	}
}

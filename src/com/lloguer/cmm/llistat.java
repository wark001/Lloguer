package com.lloguer.cmm;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lloguer.clases.Lloguer;
import com.lloguer.clases.IdeasDataSource;

public class llistat extends ListActivity {

	private IdeasDataSource ideasDataSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// La lista de items se carga en un array. Los elementos a cargar están
		// en el archivo strings.xml
		ideasDataSource = new IdeasDataSource(this);
		ideasDataSource.open();

		// Se invoca en onResume a cargarListaIdeas() para solo cargar la lista una vez en cualquier caso
		// (al iniciar la actividad y al volver a ella desde lloguer)
		//cargarListaIdeas();
		
		// Se obtiene la vista tipo lista
		ListView lv = getListView();
				
		// Se activa el filtrado de texto en la lista.
		// Cuando el usuario comience a escribir, la lista se filtrará
		// automáticamente		
		lv.setTextFilterEnabled(true);
		
		// Cuando se pulse un elemento de la lista, se mostrará el detalle de la
		// Idea lanzando un Intent a la otra actividad.
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// El id NO es el identificador (PK) de la Idea en la base de datos.
				// Es el identificador en la lista
				// Idea idea = (Idea) parent.getItemAtPosition(position);
							
				editarIdea(id);
			}
		});	
		
		// Con este método se asigna el menú contextual (para editar y borrar la idea seleccionada) a la ListView
		registerForContextMenu(lv);
	}
	
	private void cargarListaIdeas() {
		
		List<Lloguer> lloguers = ideasDataSource.getAllIdeas();
		// Cada elemento de la lista será una TextView, definido en el layout
		// lista_ideas.xml		
		//setListAdapter(new ArrayAdapter<Idea>(this, R.layout.lista_ideas, ideas));
		setListAdapter(new ArrayAdapter<Lloguer>(this, R.layout.main, lloguers));
		
//		ArrayList<HashMap<String,String>> listaIdeasFormateada = new ArrayList<HashMap<String,String>>();
//		
//		Iterator<Idea> it = ideas.iterator();
//		while (it.hasNext()) {
//			Idea idea = it.next();
//			HashMap<String,String> hash = new HashMap<String,String>();
//			hash.put(IdeasDatabaseHelper.COLUMN_TITULO_IDEA, idea.getTituloIdea());
//			hash.put(IdeasDatabaseHelper.COLUMN_TEXTO_IDEA, idea.getTextoIdea().length() > 50 ? idea.getTextoIdea().substring(0, 50) + "..." : idea.getTextoIdea());
//			listaIdeasFormateada.add(hash);
//		}
//		
//		setListAdapter(new SimpleAdapter(this, listaIdeasFormateada, R.layout.listview_dos_columnas, 
//				new String[] {IdeasDatabaseHelper.COLUMN_TITULO_IDEA, IdeasDatabaseHelper.COLUMN_TEXTO_IDEA}, 
//				new int[] {R.id.texto_lista_1, R.id.texto_lista_2}));
	}
	
	@Override
	protected void onResume() {
		ideasDataSource.open();		
		cargarListaIdeas();
		super.onResume();
	}

	@Override
	protected void onPause() {
		ideasDataSource.close();
		super.onPause();
	}
	//-------------------------------------------------------------------------
	/*
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, AlmacenamientoDatosMainActivity.class);
		// Se vuelve a la actividad anterior, sin invocar a una nueva instancia de la misma
		intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		startActivity(intent);		
	}*/
	
	// Método que creará el menú contextual para la ListView
	// El menú contextual está definido en menu_contextual_ideas.xml
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_contextual_ideas, menu);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {    	
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);                  
        return true;
    }
	
	// Acciones que se realizarán al seleccionar el menú de la actividad
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
        // Se gestiona la opción seleccionada
        switch (item.getItemId()) {
        case R.id.nueva:
            nuevaIdea();
            return true;  
        case R.id.buscar:
        	buscarIdea();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
	
	// Acciones que se realizarán al seleccionar un ítem del menú contextual
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		// Método (rocambolesco) para obtener la Idea seleccionada, lo cual 
		// puede ser útil para mostrar algún aviso o confirmación de la 
		// acción al usuario:

		// 1. El menuItem recibido es sobre el que se ha mantenido la 
		// pulsación para mostrar el menú contextual.
		// "info" contiene el identificador del menuItem seleccionado, 
		// sobre el que se realizará la acción del menú contextual.
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		// 2. Obtención del objeto de tipo Idea contenido en el menuItem seleccionado en la posición "info.id"
		// Idea idea = (Idea) ((ListView) info.targetView.getParent()).getItemAtPosition((int) info.id);
		
		switch (item.getItemId()) {
		case R.id.editar:
			editarIdea(info.id);
			return true;
		case R.id.borrar:
			borrarIdea(info.id);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void nuevaIdea() {    	
		Intent intent = new Intent(this, funcionsLloguer.class);
		Bundle bundle = new Bundle();	
		bundle.putInt(funcionsLloguer.MODO, funcionsLloguer.MODO_NUEVA_IDEA);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private void buscarIdea() {
		// Se muestra el teclado
		InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.showSoftInput(getListView(), InputMethodManager.SHOW_FORCED); 
	}
	
	private void editarIdea(long id) {
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<Lloguer> adapter = (ArrayAdapter<Lloguer>) getListAdapter();				
		// SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
		Lloguer lloguer = (Lloguer) adapter.getItem((int) id);
		
		Intent intent = new Intent(this, funcionsLloguer.class);
		Bundle bundle = new Bundle();	
		bundle.putInt(funcionsLloguer.MODO, funcionsLloguer.MODO_EDITAR_IDEA);
		bundle.putLong(funcionsLloguer.ID_IDEA, lloguer.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private void borrarIdea(long id) {
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<Lloguer> adapter = (ArrayAdapter<Lloguer>) getListAdapter();				
		Lloguer lloguer = (Lloguer) adapter.getItem((int) id);
		
		ideasDataSource.deleteIdea(lloguer);
		adapter.remove(lloguer);
		adapter.notifyDataSetChanged();
	}
}

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}*/

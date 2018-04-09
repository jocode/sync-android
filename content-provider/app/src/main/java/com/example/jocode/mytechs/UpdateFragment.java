package com.example.jocode.mytechs;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jocode.mytechs.model.TechsContract;

/**
 * Fragmento con un formulario de actualización
 */
public class UpdateFragment extends Fragment {

    /**
     *  Identificador de la actividad
     */
    private long id;
    /**
     * Views del layout
     */
    private EditText descripcion;
    private Spinner entidad;
    private Spinner prioridad;
    private Spinner estado;
    private Spinner categoria;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflamos la vista del fragment insertar
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        descripcion = (EditText) view.findViewById(R.id.descripcion_input);
        prioridad = (Spinner) view.findViewById(R.id.prioridad_spinner);
        entidad = (Spinner) view.findViewById(R.id.tecnico_spinner);
        estado = (Spinner) view.findViewById(R.id.estado_spinner);
        categoria = (Spinner) view.findViewById(R.id.categoria_spinner);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(TechsContract.Columnas._ID, -1);
        updateView(); // Cargar datos iniciales
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_insert, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                updateData(); // Actualizar
                getActivity().finish();
                return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Actualizar datos de la actividad
     */
    private void updateData() {

        // Unir Uri principal con identificador, le pasa como parámetro el id
        Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);

        ContentValues values = new ContentValues();
        values.put(TechsContract.Columnas.DESCRIPCION, descripcion.getText().toString());
        values.put(TechsContract.Columnas.PRIORIDAD, prioridad.getSelectedItem().toString());
        values.put(TechsContract.Columnas.TECNICO, entidad.getSelectedItem().toString());
        values.put(TechsContract.Columnas.ESTADO, estado.getSelectedItem().toString());
        values.put(TechsContract.Columnas.CATEGORIA, categoria.getSelectedItem().toString());

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    /**
     * Carga los datos que provienen desde el detalle
     */
    private void updateView() {
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
        String descripcion_text = i.getStringExtra(TechsContract.Columnas.DESCRIPCION);
        String prioridad_text = i.getStringExtra(TechsContract.Columnas.PRIORIDAD);
        String entidad_text = i.getStringExtra(TechsContract.Columnas.TECNICO);
        String estado_text = i.getStringExtra(TechsContract.Columnas.ESTADO);
        String categoria_text = i.getStringExtra(TechsContract.Columnas.CATEGORIA);

        // Actualizar la vista
        descripcion.setText(descripcion_text);
        prioridad.setSelection(getIndex(prioridad, prioridad_text));
        entidad.setSelection(getIndex(entidad, entidad_text));
        estado.setSelection(getIndex(estado, estado_text));
        categoria.setSelection(getIndex(categoria, categoria_text));
    }

    /**
     * Obtiene el indice de un {@link Spinner} según el valor
     * de una cadena
     *
     * @param spinner Instancia del spinner
     * @param value   Cadena a buscar
     * @return Posición donde se encuentra
     */
    private int getIndex(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                index = i;
                break;
            }
        }
        return index;
    }


}

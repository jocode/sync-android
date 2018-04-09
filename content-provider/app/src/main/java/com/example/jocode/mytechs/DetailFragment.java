package com.example.jocode.mytechs;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.example.jocode.mytechs.model.TechsContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    // Textos del Layout
    private TextView descripcion, categoria, entidad, prioridad, estado;

    // Identificador de la Actividad
    private long id;

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);

        // Obtención de views
        descripcion = (TextView) view.findViewById(R.id.description_text);
        categoria = (TextView) view.findViewById(R.id.categoria_text);
        entidad = (TextView) view.findViewById(R.id.tecnico_text);
        prioridad = (TextView) view.findViewById(R.id.prioridad_text);
        estado = (TextView) view.findViewById(R.id.estado_text);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(TechsContract.Columnas._ID, -1);
        // Actualzar la vista con los datos de la actividad
        updateView(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                beginUpdate(); // Actualizar
                return true;
            case R.id.action_delete:
                deleteData(); // Eliminar
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        getActivity()
                .startActivity(
                        new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(TechsContract.Columnas._ID, id)
                                .putExtra(TechsContract.Columnas.DESCRIPCION, descripcion.getText())
                                .putExtra(TechsContract.Columnas.CATEGORIA, categoria.getText())
                                .putExtra(TechsContract.Columnas.TECNICO, entidad.getText())
                                .putExtra(TechsContract.Columnas.PRIORIDAD, prioridad.getText())
                                .putExtra(TechsContract.Columnas.ESTADO, estado.getText())
                );
    }

    /**
     * Actualiza los textos del layout
     *
     * @param id Identificador de la actividad
     */
    private void updateView(long id) {
        if (id == -1) {
            descripcion.setText("");
            categoria.setText("");
            entidad.setText("");
            prioridad.setText("");
            estado.setText("");

            return;
        }

        Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
        Cursor c = getActivity().getContentResolver().query(
                uri,
                null, null, null, null);

        if (!c.moveToFirst())
            return;

        String descripcion_text = c.getString(c.getColumnIndex(TechsContract.Columnas.DESCRIPCION));
        String categoria_text = c.getString(c.getColumnIndex(TechsContract.Columnas.CATEGORIA));
        String entidad_text = c.getString(c.getColumnIndex(TechsContract.Columnas.TECNICO));
        String prioridad_text = c.getString(c.getColumnIndex(TechsContract.Columnas.PRIORIDAD));
        String estado_text = c.getString(c.getColumnIndex(TechsContract.Columnas.ESTADO));

        descripcion.setText(descripcion_text);
        categoria.setText(categoria_text);
        entidad.setText(entidad_text);
        prioridad.setText(prioridad_text);
        estado.setText(estado_text);

        c.close(); // Liberar memoria del cursor
    }


    /**
     * Elimina el item de la actividad actual usando el content provider
     */
    private void deleteData() {
        Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
        getActivity().getContentResolver().delete(
                uri,
                null,
                null
        );
    }


}

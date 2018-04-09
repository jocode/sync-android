package com.example.jocode.mytechs;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jocode.mytechs.adapters.ActivitiesAdapter;
import com.example.jocode.mytechs.model.TechsContract;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // Declaración del Adaptador
    private ActivitiesAdapter adapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Otras acciones
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goInsertActivity();
                /**
                 * Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show();
                 */
            }
        });

        return view;
    }

    private void goInsertActivity() {
        startActivity(new Intent(getActivity(), InsertActivity.class));
    }

    /**
     * Iniciar la actividad Detail, al dar clic en un item
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(TechsContract.Columnas._ID, id);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Iniciar adaptador
        adapter = new ActivitiesAdapter(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adapter);
        // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Consultar todos los registros
        return new CursorLoader(
                getActivity(),
                TechsContract.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        /**
         * Aquí intercambiaremos el cursor nulo que  tiene el adaptador por el cursor obtenido
         * de la consulta  realizada por el loader
         */
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Intercambiamos el cursor antigui por uno nulo
        adapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adapter != null) {
                adapter.changeCursor(null);
                adapter = null;
            }
        } catch (Throwable localThrowable) {
            // Proyectar la excepción
        }
    }
}

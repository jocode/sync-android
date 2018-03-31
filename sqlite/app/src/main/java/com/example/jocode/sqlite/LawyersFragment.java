package com.example.jocode.sqlite;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jocode.sqlite.addeditlawyer.AddEditLawyerActivity;
import com.example.jocode.sqlite.data.LawyersContract;
import com.example.jocode.sqlite.data.LawyersDbHelper;
import com.example.jocode.sqlite.lawyerdetail.LawyerDetailActivity;


public class LawyersFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;
    private LawyersDbHelper mLawyersDbHelper;

    private ListView mLawyersList;
    private LawyersCursorAdapter mLawyersAdapter;
    private FloatingActionButton mAddButton;

    public LawyersFragment() {
        // Required empty public constructor
    }

    public static LawyersFragment newInstance() {
        return new LawyersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lawyers, container, false);

        // Referencias UI
        mLawyersList = (ListView) root.findViewById(R.id.lawyers_list);
        mLawyersAdapter = new LawyersCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mLawyersList.setAdapter(mLawyersAdapter);

        // Instancia de helper
        mLawyersDbHelper = new LawyersDbHelper(getActivity());

        // Carga de datos
        loadLawyers();

        // Eventos
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(LawyersContract.LawyerEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        return root;
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditLawyerActivity.class);
        startActivityForResult(intent, AddEditLawyerActivity.REQUEST_ADD_LAWYER);
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), LawyerDetailActivity.class);
        intent.putExtra(LawyersActivity.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    /**
     * La aparición del método onActivityResult() se debe a que la lista que tendrá el fragmento
     * debe refrescarse en caso de que las screens de inserción o detalle hayan producido una
     * modificación de la tabla lawyer.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado fue positivo, actualizamos la lista
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditLawyerActivity.REQUEST_ADD_LAWYER:
                    showSuccessfullSavedMessage();
                    loadLawyers();
                    break;
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadLawyers();
                    break;
            }
        }
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void loadLawyers() {
        // Cargar datos...
        new LawyersLoadTask().execute();
    }

    /**
     * Crea una tarea asíncrona dentro del fragmento, la cuál reciba como resultado un Cursor.
     * Esto con el fin de no entorpecer el hilo principal con el acceso a la base de datos.
     */
    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getAllLawyers();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mLawyersAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}

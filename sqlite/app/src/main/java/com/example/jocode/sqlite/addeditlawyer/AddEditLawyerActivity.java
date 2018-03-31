package com.example.jocode.sqlite.addeditlawyer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jocode.sqlite.LawyersActivity;
import com.example.jocode.sqlite.R;

public class AddEditLawyerActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_LAWYER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_lawyer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String lawyerId = getIntent().getStringExtra(LawyersActivity.EXTRA_LAWYER_ID);

        setTitle(lawyerId == null ? "Añadir abogado" : "Editar abogado");

        AddEditLawyerFragment addEditLawyerFragment = (AddEditLawyerFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_lawyer_container);
        if (addEditLawyerFragment == null) {
            addEditLawyerFragment = AddEditLawyerFragment.newInstance(lawyerId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_lawyer_container, addEditLawyerFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

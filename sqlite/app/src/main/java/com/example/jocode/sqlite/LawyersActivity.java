package com.example.jocode.sqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LawyersActivity extends AppCompatActivity {

    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LawyersFragment lawyersFragment = (LawyersFragment) getSupportFragmentManager().findFragmentById(R.id.lawyers_container);

        if (lawyersFragment == null){
            lawyersFragment = LawyersFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.lawyers_container, lawyersFragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

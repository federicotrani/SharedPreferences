package com.example.ftrani.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREFS_NAME = "MiArchivoPrefs";

    //controles UI
    private EditText edtNombre;
    private EditText edtEmail;
    private Button btnGrabar;
    private Button btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referencia controles UI
        bindUI();

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombreUsuario","--");
        String email = preferences.getString("emailUsuario","--");

        this.asignarValores(nombre, email);
    }

    private void asignarValores(String nombre, String email){
        edtNombre.setText(nombre);
        edtEmail.setText(email);
    }

    private void bindUI(){
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edtNombre.getText().toString();
                String email = edtEmail.getText().toString();
                grabarValores(nombre, email);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarValores();
            }
        });
    }

    private void grabarValores(String nombre, String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombreUsuario", nombre);
        editor.putString("emailUsuario", email);
        editor.commit();
        //notificacion Mediante Snackbar -> requiere Support Design Library
        Snackbar.make(findViewById(R.id.main_layout),"Valores Grabados OK",Snackbar.LENGTH_SHORT).show();

    }

    private void limpiarValores(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        edtNombre.setText("");
        edtEmail.setText("");
        //notificacion Mediante Snackbar -> requiere Support Design Library
        Snackbar.make(findViewById(R.id.main_layout),"Valores Borrados OK",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuLimpiar:
                this.limpiarValores();
                break;
            case R.id.mnuSalir:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

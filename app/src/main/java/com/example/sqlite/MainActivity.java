package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ID, Usuario, AreaUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID = findViewById(R.id.txtID);
        Usuario = findViewById(R.id.txtNombreUsuario);
        AreaUsuario = findViewById(R.id.txtAreaUsuario);
    }

    public void RegistrarUsuario(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        String NombreUsuario = Usuario.getText().toString();
        String Areausuario = AreaUsuario.getText().toString();
        if(!IDUsuario.isEmpty() && !NombreUsuario.isEmpty()
                && !Areausuario.isEmpty()){
            ContentValues DatosUsuario = new ContentValues();
            DatosUsuario.put("ID_Usuario", IDUsuario);
            DatosUsuario.put("NombreUsuario", NombreUsuario);
            DatosUsuario.put("AreaUsuario", Areausuario);
            baseDatos.insert("Usuarios", null, DatosUsuario);
            baseDatos.close();
            ID.setText("");
            Usuario.setText("");
            AreaUsuario.setText("");
            Toast.makeText(this, "Se ha registrado el usuario correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No pueden haber campos vacios!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarUsuario(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        if(!IDUsuario.isEmpty()){
            Cursor fila = baseDatos.rawQuery("Select NombreUsuario, AreaUsuario  from Usuarios where ID_Usuario="+ IDUsuario, null );
            if (fila.moveToFirst()){
                Usuario.setText(fila.getString(0));
                AreaUsuario.setText(fila.getString(1));
                baseDatos.close();
            } else {
                Toast.makeText(this, "El ID ingresado no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El campo ID no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
    }
    public void EliminarUsuario(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        if(!IDUsuario.isEmpty()){
            int Eliminar = baseDatos.delete("Usuarios","ID_Usuario=" + IDUsuario, null);
            if (Eliminar == 1){
                Toast.makeText(this, "Se eliminó el usuario.", Toast.LENGTH_SHORT).show();
                baseDatos.close();
                ID.setText("");
                Usuario.setText("");
                AreaUsuario.setText("");
            }else{
                Toast.makeText(this, "No se encontró el ID", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "el campo ID no puede estar vacío", Toast.LENGTH_SHORT).show();
        }
    }
    public void ModificarUsuario (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Produccion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String IDUsuario = ID.getText().toString();
        String NombreUsuario = Usuario.getText().toString();
        String Areausuario = AreaUsuario.getText().toString();
        if (!IDUsuario.isEmpty() && !NombreUsuario.isEmpty() && !Areausuario.isEmpty()){
            ContentValues DatosUsuario = new ContentValues();
            DatosUsuario.put("NombreUsuario", NombreUsuario);
            DatosUsuario.put("Areausuario", Areausuario);
            int Modificar = baseDatos.update("Usuarios", DatosUsuario, "ID_Usuario="+IDUsuario, null);
            if(Modificar == 1){
                Toast.makeText(this, "Se modificó el usuario", Toast.LENGTH_SHORT).show();
                baseDatos.close();
                ID.setText("");
                Usuario.setText("");
                AreaUsuario.setText("");
            }else {
                Toast.makeText(this,"No se modificó el usuario", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "No pueden haber campos vacios",Toast.LENGTH_SHORT).show();
        }
    }
}
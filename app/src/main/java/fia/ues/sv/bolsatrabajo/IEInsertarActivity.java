package fia.ues.sv.bolsatrabajo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class IEInsertarActivity extends Activity  implements AdapterView.OnItemSelectedListener {
    Spinner sp;
    ArrayAdapter<CharSequence> adapter;
    String deptoIE, nomIE, estadoSQL;
    EditText editNomIE;
    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieinsertar);
        helper= new ControlBD(this);
        sp = (Spinner) findViewById(R.id.spinnerDepartamentos);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinnerDepartamentos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        editNomIE = (EditText)findViewById(R.id.editNomIE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ieinsertar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        deptoIE = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(this, deptoIE, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //
    }
    public void insertar(View v){
        nomIE=editNomIE.getText().toString();
        if(nomIE.length()==0||deptoIE.contentEquals("- - -")){
            tostar("Campos no llenos",1);
        }else{
            helper.abrir();
            InstitucionEducacion IE = new InstitucionEducacion();
            IE.setNombreIE(nomIE);
            IE.setDeptoIE(deptoIE);
            //Toast.makeText(this,IE.getNombreIE(), Toast.LENGTH_LONG).show();
            //Toast.makeText(this,IE.getDeptoIE(), Toast.LENGTH_LONG).show();
            estadoSQL = helper.insertarIE(IE);
            //System.out.println(estadoSQL);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editNomIE.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
            tostar(estadoSQL,2);
            helper.cerrar();
            editNomIE.setText("");
            sp.setAdapter(adapter);
        }
    }
    private void tostar(String msj,int opc){//un metodo para cambiar el color del Toast
        Color color = new Color();
        int c=0;
        Toast t = Toast.makeText(this,msj, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0, 25);
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        switch (opc){
            case 0: c= color.parseColor("BLACK");
                break;
            case 1: c= color.parseColor("RED");
                break;
            case 2: c= color.parseColor("CYAN");
                break;
        }
        v.setTextColor(c);
        t.show();
    }
}
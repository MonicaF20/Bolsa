package fia.ues.sv.bolsatrabajo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
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


public class IEActualizarActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner sp;
    private ArrayAdapter<CharSequence> adapter;
    private ControlBD helper;
    private Integer idIE;
    private String deptoIE,nomIE,estadoSQL;
    private EditText editIDIE,editNomIE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieactualizar);
        helper=new ControlBD(this);
        editIDIE =(EditText)findViewById(R.id.editIDIE);
        editNomIE = (EditText)findViewById(R.id.editNomIE);
        //editDeptoIE = (EditText)findViewById(R.id.editDeptoIE);
        sp= (Spinner)findViewById(R.id.spinnerDepartamentos);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinnerDepartamentos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setOnItemSelectedListener(this);
        sp.setAdapter(adapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        deptoIE = parent.getItemAtPosition(position).toString();
        editIDIE.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editIDIE.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ieactualizar, menu);
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
    public void consultarIE(View v){
        //System.out.println("LINEA"+editIDIE.length());
        if (editIDIE.length()==0){
            tostar("Ingrese un ID válido por favor",1);
            editIDIE.setText("");
            editNomIE.setText("");
        }else {
            InstitucionEducacion IE = new InstitucionEducacion();
            IE.setIdIE(Integer.parseInt(editIDIE.getText().toString()));
            idIE = IE.getIdIE();
            helper.abrir();
            IE = helper.consultar(IE);
            if(IE==null){tostar("Institución no encontrada",1);}
            else{
                editIDIE.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editIDIE.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
                tostar("Resultado encontrado para id: " + editIDIE.getText().toString(), 2);
                editNomIE.setText(IE.getNombreIE());
                sp.setSelection(getNumDpto(IE.getDeptoIE()));//Método para setear el spinner en base al departamento de la bases
                editIDIE.setText("");
            }
            helper.cerrar();
        }
    }
    public void actualizarIE(View v){
        nomIE=editNomIE.getText().toString();
        if(nomIE.length()==0||deptoIE.contentEquals("- - -")){
            tostar("Campos no llenos",1);
        }else{
            helper.abrir();
            InstitucionEducacion IE = new InstitucionEducacion();
            IE.setIdIE(idIE);
            IE.setNombreIE(nomIE);
            IE.setDeptoIE(deptoIE);
            estadoSQL = helper.actualizar(IE);
            System.out.println(estadoSQL);
            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(editNomIE.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
            if(estadoSQL.contentEquals("Error, institución no fue modificada"))
                tostar(estadoSQL,1);
            else {tostar(estadoSQL,2);}
            helper.cerrar();
            editNomIE.setText("");
            sp.setAdapter(adapter);
        }
    }
    private int getNumDpto(String dpto) {
        int n=0;
        switch (dpto){
            case "Ahuachapán": n+=1;
                break;
            case "Cabañas": n+=2;
                break;
            case "Chalatenango": n+=3;
                break;
            case "Cuscatlán": n+=4;
                break;
            case "La Libertad": n+=5;
                break;
            case "La Paz": n+=6;
                break;
            case "La Unión": n+=7;
                break;
            case "Morazán": n+=8;
                break;
            case "Santa Ana": n+=9;
                break;
            case "San Miguel": n+=10;
                break;
            case "San Salvador": n+=11;
                break;
            case "San Vicente": n+=12;
                break;
            case "Sonsonate": n+=13;
                break;
            case "Usulután": n+=14;
                break;
        }
        return n;
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

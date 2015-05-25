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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class IEConsultarActivity extends Activity {
    String idIE,deptoIE, nomIE, estadoSQL;
    EditText editNomIE,editDeptoIE,editIDIE;
    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieconsultar);
        editNomIE=(EditText)findViewById(R.id.editNomIns);
        editDeptoIE=(EditText)findViewById(R.id.editDepto);
        editIDIE=(EditText)findViewById(R.id.editIDIE);
        helper= new ControlBD(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ieconsultar, menu);
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
            editNomIE.setText(" ");
            editDeptoIE.setText(" ");
        }else {
            InstitucionEducacion IE = new InstitucionEducacion();
            IE.setIdIE(Integer.parseInt(editIDIE.getText().toString()));
            helper.abrir();
            IE = helper.consultar(IE);
            if(IE==null){tostar("Institución no encontrada",1);}
            else{
                editIDIE.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editIDIE.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
                tostar("Resultado encontrado para id: " + editIDIE.getText().toString(), 2);
                editNomIE.setText(IE.getNombreIE());
                editDeptoIE.setText(IE.getDeptoIE());
                editIDIE.setText("");
            }
            helper.cerrar();
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

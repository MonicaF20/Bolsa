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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OLConsultarActivity extends Activity {
    private ControlBD helper;
    private EditText editIDOL,editIDEmp,editIDCar,editFechaP,editFechaX;
    private String idEmp,idCar,fechaP,fechaX,QState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olconsultar);
        helper = new ControlBD(this);
        editIDOL = (EditText)findViewById(R.id.editIDOL);
        editIDEmp = (EditText)findViewById(R.id.editIDEmpOL);
        editIDCar = (EditText)findViewById(R.id.editIDCarOL);
        editFechaP = (EditText)findViewById(R.id.editFechaPubOL);
        editFechaX = (EditText)findViewById(R.id.editFechaExpOL);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_olconsultar, menu);
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
    public void consultarOL(View v){
        if (editIDOL.length()==0) {
            tostar("Ingrese un ID válido por favor", 1);
            editIDOL.setText("");
            editIDEmp.setText("");
            editIDCar.setText("");
            editFechaP.setText("");
            editFechaX.setText("");
        }else {
            OfertaLaboral OL = new OfertaLaboral();
            OL.setIdOL(Integer.parseInt(editIDOL.getText().toString()));
            helper.abrir();
            OL = helper.consultar(OL);
            if (OL == null) {
                tostar("Oferta Laboral no encontrada", 1);
                editIDOL.setText("");
                editIDEmp.setText("");
                editIDCar.setText("");
                editFechaP.setText("");
                editFechaX.setText("");
            } else {
                //editIDOL.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editIDOL.getWindowToken(), 0);//---PARA ESCONDER EL TECLADO
                tostar("Resultado encontrado para id: " + editIDOL.getText().toString(), 2);
                idEmp= String.valueOf(OL.getIdEmp());
                editIDEmp.setText(idEmp);
                idCar = String.valueOf(OL.getIdCar());
                editIDCar.setText(idCar);
                fechaP=OL.getFechaP();
                editFechaP.setText(fechaP);
                fechaX=OL.getFechaX();
                editFechaX.setText(fechaX);
                editIDOL.setText("");
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

package fia.ues.sv.bolsatrabajo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class OLInsertarActivity extends Activity {
    private ControlBD helper;
    private EditText editIDEmp,editIDCar,editFechaP,editFechaX;
    private String idEmp,idCar,fechaP,fechaX,QState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olinsertar);
        helper = new ControlBD(this);
        editIDEmp = (EditText)findViewById(R.id.editIDEmpOL);
        editIDCar = (EditText)findViewById(R.id.editIDCarOL);
        editFechaP = (EditText)findViewById(R.id.editFechaPubOL);
        editFechaX = (EditText)findViewById(R.id.editFechaExpOL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_olinsertar, menu);
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
    public void insertarOL(View v){
        idEmp=editIDEmp.getText().toString();
        idCar=editIDCar.getText().toString();
        fechaP=editFechaP.getText().toString();
        fechaX=editFechaX.getText().toString();
        if (idEmp.length()==0||idCar.length()==0||fechaP.length()==0||fechaX.length()==0){
            tostar("Campos vacíos",1);
        }else{
            if(!(Integer.parseInt(idEmp)==0||Integer.parseInt(idCar)==0)){
                if (validarFecha(fechaP)&&validarFecha(fechaX)) {
                    helper.abrir();
                    if (validarEmp(idEmp)) {
                        if (validarCar(idCar)) {
                            OfertaLaboral OL = new OfertaLaboral();
                            OL.setIdEmp(Integer.parseInt(idEmp));
                            OL.setIdCar(Integer.parseInt(idCar));
                            OL.setFechaP(fechaP);
                            OL.setFechaX(fechaX);
                            QState = helper.insertar(OL);
                            editIDEmp.setText("");
                            editIDCar.setText("");
                            editFechaP.setText("");
                            editFechaX.setText("");
                            tostar(QState,2);
                        }
                    }
                    helper.cerrar();
                }else{
                    tostar("Formato de fecha inválido, \ndebe ser dd/mm/aaaa",0);
                }
            }else {
                tostar("'0' no es un ID válido",1);
            }
        }
    }

    private boolean validarCar(String idCar) {
        boolean res=helper.valCar(idCar);
        if(!res){tostar("Consulte ID del cargo",1);}
        return res;
    }
    private boolean validarEmp(String idEmp) {
        boolean res=helper.valEmp(idEmp);
        if(!res){tostar("Consulte ID de la empresa",1);}
        return res;
    }
    private void tostar(String msj,int opc){//un metodo para cambiar el color del Toast
        Color color = new Color();
        int c=0;
        Toast t = Toast.makeText(this,msj, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0, 25);
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        switch (opc){
            case 0: c= color.parseColor("GRAY");
                break;
            case 1: c= color.parseColor("RED");
                break;
            case 2: c= color.parseColor("CYAN");
                break;
        }
        v.setTextColor(c);
        t.show();
    }
    private boolean validarFecha(String f){
        boolean ok=false;
        if (!(f.length()>10||f.length()<10)) {
            //tostar("Tamaño de la cadena cumplió",2);
            char[] cad=f.toCharArray();
            String aux="";
            for (int i=0;i<=1;i++){aux=aux+cad[i];}
            if (Integer.parseInt(aux)>31){
                ok=false;
                //tostar("dia incorrecto",1);
            }
            else {
                aux="";
                for (int i=3;i<=4;i++){aux=aux+cad[i];/*tostar(aux,2);*/}
                //Integer p =Integer.parseInt(aux);
                //tostar(p.toString(),1);
                if (Integer.parseInt(aux)>12){
                    ok=false;
                    //tostar("mes incorrecto",1);
                }
                else {
                    aux="";
                    for (int i=6;i<=9;i++){aux=aux+cad[i];}
                    if (Integer.parseInt(aux)>2016||Integer.parseInt(aux)<=2014){//OfertasLaborales solo durante 2015/2016
                        ok =false;
                        //tostar("año incorrecto",1);
                    }
                    else{ok = true;} //fecha valida
                }
            }
        }
        //else{tostar("Formato de fecha inválido,\n pruebe con dd/mm/aaaa",1);}
        return ok;
    }
    public void helpEmp(View v){
        try {
            Intent nueva = new Intent(this,EmpresaConsultarActivity.class);
            startActivity(nueva);
        } catch (Exception e) {
            e.printStackTrace();
            tostar("Activity EMPRESACONSULTAR no existe",1);
        }
    }
    public void helpCar(View v){
        Intent nueva = new Intent(this,CargoConsultarActivity.class);
        startActivity(nueva);
    }
}

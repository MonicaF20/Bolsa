package fia.ues.sv.bolsatrabajo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class OLActualizarActivity extends Activity {
    private ControlBD helper;
    private EditText editIDOL,editIDEmp,editIDCar,editFechaP,editFechaX;
    private String QState,x;
    private OfertaLaboral OL;
    private Integer idOL;
    private String idEmp,idCar,fechaP,fechaX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olactualizar);
        helper = new ControlBD(this);
        editIDOL = (EditText)findViewById(R.id.editIDOL);
        editIDEmp = (EditText)findViewById(R.id.editIDEmpOL);
        editIDCar = (EditText)findViewById(R.id.editIDCarOL);
        editFechaP = (EditText)findViewById(R.id.editFechaPubOL);
        editFechaX = (EditText)findViewById(R.id.editFechaExpOL);
        OL =new OfertaLaboral();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_olactualizar, menu);
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
            helper.abrir();
            OfertaLaboral OL = new OfertaLaboral();
            idOL=Integer.parseInt(editIDOL.getText().toString());
            OL.setIdOL(idOL);
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
    public void actualizarOL(View v){
        if(editIDEmp.length()==0||editIDCar.length()==0||editFechaP.length()==0||editFechaX.length()==0){
            tostar("Campos vacíos",1);
        }else{
            idEmp=editIDEmp.getText().toString();
            idCar=editIDCar.getText().toString();
            fechaP=editFechaP.getText().toString();
            fechaX=editFechaX.getText().toString();
            x=editFechaX.getText().toString();
            if (!(Integer.parseInt(idEmp)==0||Integer.parseInt(idCar)==0)){
                if (validarFecha(x)) {
                    helper.abrir();
                    if (validarEmp(idEmp)) {
                        if (validarCar(idCar)) {
                            //tostar(OL.getIdOL().toString(),1);
                            OfertaLaboral OL =new OfertaLaboral();
                            //idEmp=editIDEmp.getText().toString();
                            //idCar=editIDCar.getText().toString();
                            //fechaP=editFechaP.getText().toString();
                            //fechaX=editFechaX.getText().toString();
                            OL.setIdEmp(Integer.parseInt(idEmp));
                            OL.setIdCar(Integer.parseInt(idCar));
                            OL.setFechaP(fechaP);
                            OL.setFechaX(fechaX);
                            OL.setIdOL(idOL);
                            QState = helper.actualizar(OL);
                /*if(QState.contentEquals("Error, no fue modificado"))
                    tostar(QState,1);
                else {*/
                            tostar(QState,2);
                            editIDOL.setText("");
                            editIDEmp.setText("");
                            editIDCar.setText("");
                            editFechaP.setText("");
                            editFechaX.setText("");
                            //}
                        }
                    }
                    helper.cerrar();
                }
            }else{tostar("El '0' no es un ID válido",1);}
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
    private boolean validarFecha(String f){
        boolean ok=false;
        if (!(f.length()>10||f.length()<10)) {
            //tostar("Tamaño de la cadena cumplió",2);
            char[] cad=f.toCharArray();
            String aux="";
            for (int i=0;i<=1;i++){aux=aux+cad[i];}
            if (Integer.parseInt(aux)>31){
                ok=false;
                tostar("dia incorrecto",1);
            }
            else {
                aux="";
                for (int i=3;i<=4;i++){aux=aux+cad[i];/*tostar(aux,2);*/}
                //Integer p =Integer.parseInt(aux);
                //tostar(p.toString(),1);
                if (Integer.parseInt(aux)>12){
                    ok=false;
                    tostar("mes incorrecto",1);
                }
                else {
                    aux="";
                    for (int i=6;i<=9;i++){aux=aux+cad[i];}
                    if (Integer.parseInt(aux)>2016||Integer.parseInt(aux)<=2014){//OfertasLaborales solo durante 2015/2016
                        ok =false;
                        tostar("año incorrecto",1);
                    }
                    else{ok = true;} //fecha valida
                }
            }
        }
        else{tostar("Formato de fecha inválido,\n pruebe con dd/mm/aaaa",1);}
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

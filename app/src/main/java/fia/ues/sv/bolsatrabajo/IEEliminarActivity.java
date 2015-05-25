package fia.ues.sv.bolsatrabajo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class IEEliminarActivity extends Activity {
    String deptoIE, nomIE, estadoSQL;
    int idIE;
    EditText editNomIE,editDeptoIE,editIDIE;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieeliminar);
        helper=new ControlBD(this);
        editIDIE =(EditText)findViewById(R.id.editIDIE);
        editNomIE = (EditText)findViewById(R.id.editNomIE);
        editDeptoIE = (EditText)findViewById(R.id.editDeptoIE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ieeliminar, menu);
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
            editDeptoIE.setText("");
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
                editDeptoIE.setText(IE.getDeptoIE());
                editIDIE.setText("");
            }
            //helper.cerrar();
        }
    }
    public void eliminarIE(View v){
        if (editNomIE.length()==0||editDeptoIE.length()==0){
            tostar("Nada que eliminar",1);
        }else{
            InstitucionEducacion IE = new InstitucionEducacion();
            nomIE=editNomIE.getText().toString();
            deptoIE=editDeptoIE.getText().toString();
            IE.setIdIE(idIE);
            IE.setNombreIE(nomIE);
            IE.setDeptoIE(deptoIE);
            estadoSQL = helper.eliminar(IE);
            editIDIE.setText("");
            editNomIE.setText(" ");
            editDeptoIE.setText(" ");
            tostar(estadoSQL,2);
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

    //------------------------CLASE EXTRA POPUP WINDOW------------------------//
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public static class dialogoAlerta extends DialogFragment {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.alerta)
                       .setMessage(R.string.dialogo)
                       .setPositiveButton(R.string.opEliminar, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               System.out.println("ELIMINAR ;)");
                           }
                       })
                       .setNegativeButton(R.string.opcCancelar, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               System.out.println("Operación cancelada");
                           }
                       });
                // Create the AlertDialog object and return it
                return builder.create();
            }
        }
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void eliminarIE2(View v){
            //System.out.println("btn_eliminar");
            dialogoAlerta da= new dialogoAlerta();
            da.show(getFragmentManager(),"ALERTA");
        }
}

package fia.ues.sv.bolsatrabajo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ReferenciaEliminarActivity extends ActionBarActivity {

    ControlBD helper;
    EditText editIdReferencia;
    EditText editIdEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencia_eliminar);
        helper=new ControlBD(this);
        editIdReferencia=(EditText)findViewById(R.id.editIdReferencia);
        editIdEmpleado=(EditText)findViewById(R.id.editIdEmpleado);

    }

    public void eliminarReferenciaAct(View view){
        String regDelete;
        String id=editIdReferencia.getText().toString();
        String idEmple=editIdEmpleado.getText().toString();
        Referencia referencia=new Referencia();
        referencia.setId_referencia(Integer.parseInt(id));
        referencia.setId_empleado(Integer.parseInt(idEmple));
        helper.abrir();
        regDelete=helper.eliminar(referencia);
        helper.cerrar();
        Toast.makeText(this,regDelete,Toast.LENGTH_SHORT).show();
    }

}

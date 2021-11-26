package calculator.equationCalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;

import com.gpashev.contacts2.R;

import java.util.ArrayList;

public class ViewDelete extends DBActivity {
    protected Button btnDelete;
    protected String ID;
    protected ListView simpleList;
    protected void FillListView() throws Exception{
        final ArrayList<String> listResults=
                new ArrayList<>();
        SelectSQL(
                "SELECT * FROM CalculationHistory",
                null,
                (ID, Equation, X1, X2)->{
                    listResults.add(ID+"\t Equation: "+Equation+"\t X1="+X1+"\t X2="+X2+"\n");
                }
        );
        simpleList.clearChoices();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_listview,
                R.id.textView,
                listResults

        );
        simpleList.setAdapter(arrayAdapter);
    }

//    @Override
//    @CallSuper
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        try {
//            FillListView();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    private void BackToMain(){
//        finishActivity(200);
//        Intent i=new Intent(ViewDelete.this, MainActivity.class);
//        startActivity(i);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delete);
        btnDelete=findViewById(R.id.btnDelete);
        simpleList=findViewById(R.id.simpleList);
        try {
            FillListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDelete.setOnClickListener(view -> {
            try{
                ExecSQL("DELETE FROM CalculationHistory WHERE " +
                        "ID = ?",
                        new Object[]{ID},
                        ()-> Toast.makeText(getApplicationContext(),
                                "Delete Successful", Toast.LENGTH_LONG).show()
                        );
            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),
                        "Delete Error: "+exception.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }finally {
                ID="-1";
                btnDelete.setEnabled(false);
                try {
                    FillListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedText=view.findViewById(R.id.textView);
                String selected = clickedText.getText().toString();
                String[] elements=selected.split("\t");
                ID=elements[0];
                btnDelete.setEnabled(true);
            }
        });

    }
}
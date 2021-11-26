package calculator.equationCalc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gpashev.contacts2.R;

import java.text.DecimalFormat;

public class MainActivity extends DBActivity {
    protected EditText editA, editB, editC;
    protected Button btnCalculate,btnViewHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editA =findViewById(R.id.editA);
        editB =findViewById(R.id.editB);
        editC =findViewById(R.id.editC);
        btnCalculate =findViewById(R.id.btnCalculate);
        btnViewHistory =findViewById(R.id.btnViewHistory);

        try {
            initDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnViewHistory.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, ViewDelete.class);
            startActivity(intent);
        });

        btnCalculate.setOnClickListener(view -> {
            try {
                validation(editA,editC, editB);
                DecimalFormat precision = new DecimalFormat("0.00");

                double a,b,c;
                String x1="",x2="";
                a=Double.parseDouble(editA.getText().toString());
                b=Double.parseDouble(editB.getText().toString());
                c=Double.parseDouble(editC.getText().toString());
                double determinant=Math.pow(b,2)-(4*a*c);

                if(determinant>0){
                    x1= String.valueOf(precision.format((Math.sqrt(determinant)-b)/(2*a)));
                    x2= String.valueOf(precision.format((-Math.sqrt(determinant)-b)/(2*a)));
                    Toast.makeText(getApplicationContext(),
                            "Determinant is: " + determinant + "\n X1=" + x1 + "\n X2=" + x2, Toast.LENGTH_LONG).show();
                }else  if(determinant==0){
                    x1= String.valueOf(precision.format((-b)/(2*a)));
                    Toast.makeText(getApplicationContext(),
                            "Determinant is: " + determinant + "\n X=" + x1, Toast.LENGTH_LONG).show();
                } else{
                    double tempDeterminant=Math.abs(determinant);
                    x1= "(-"+b+"+"+precision.format(Math.sqrt(tempDeterminant))+"i)/2*"+a;
                    x2= "(-"+b+"-"+precision.format(Math.sqrt(tempDeterminant))+"i)/2*"+a;
                    Toast.makeText(getApplicationContext(),
                            "Determinant is: " + determinant + "\n X1=" + x1 + "\n X2=" + x2, Toast.LENGTH_LONG).show();
                }

                String equation=a + "x^2+" + b + "x+" + c + "=0";

                ExecSQL(
                        "INSERT INTO CalculationHistory(Equation, X1, X2) " +
                                "VALUES(?, ?, ?) ",
                        new Object[]{
                                equation,
                                x1,
                                x2
                        },
                        ()-> Toast.makeText(getApplicationContext(),
                                "Record Inserted", Toast.LENGTH_LONG).show()

                );

                Intent intent=new Intent(MainActivity.this, ViewDelete.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),
                        "Insert Failed: "+e.getLocalizedMessage()
                        , Toast.LENGTH_SHORT).show();

            }

        });

    }


}
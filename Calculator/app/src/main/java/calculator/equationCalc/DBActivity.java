package calculator.equationCalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBActivity extends AppCompatActivity {
    protected interface OnQuerySuccess{
        public void OnSuccess();
    }
    protected interface OnSelectSuccess{
        public void OnElementSelected(
                String ID, String Equation, String X1, String X2
        );
    }

    protected boolean matchString(String string_, String regexp){
        final String regex = regexp;
        final String string = string_;

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            return true;
        }
        return false;
    }
    protected void validation(EditText editA,EditText editB, EditText editC) throws Exception {
        if(!matchString(editA.getText().toString(), "^-?\\d*\\.{0,1}\\d+$")){

            throw new Exception("Invalid A");
        }
        if(!matchString(editB.getText().toString(), "^-?\\d*\\.{0,1}\\d+$")){

            throw new Exception("Invalid B");
        }
        if(!matchString(editC.getText().toString(), "^-?\\d*\\.{0,1}\\d+$")){

            throw new Exception("Invalid C");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void SelectSQL(String SelectQ,
                             String[] args,
                             OnSelectSuccess success
    )
            throws Exception
    {
        SQLiteDatabase db=SQLiteDatabase
                .openOrCreateDatabase(getFilesDir().getPath()+"/CalculationHistory.db", null);
        Cursor cursor=db.rawQuery(SelectQ, args);
        while (cursor.moveToNext()){
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String Equation=cursor.getString(cursor.getColumnIndex("Equation"));
            String X1=cursor.getString(cursor.getColumnIndex("X1"));
            String X2=cursor.getString(cursor.getColumnIndex("X2"));
            success.OnElementSelected(ID, Equation, X1, X2);
        }
        db.close();
    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success)
            throws Exception
    {
        SQLiteDatabase db=SQLiteDatabase
                .openOrCreateDatabase(getFilesDir().getPath()+"/CalculationHistory.db", null);
        if(args!=null)
            db.execSQL(SQL, args);
        else
            db.execSQL(SQL);

        db.close();
        success.OnSuccess();
    }
    protected void initDB() throws  Exception{
        ExecSQL(
                "CREATE TABLE if not exists CalculationHistory( " +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Equation text not null, " +
                        "X1 text null, " +
                        "X2 text null " +
                        ")",
                null,
                ()-> Toast.makeText(getApplicationContext(),
                        "DB Init Successful", Toast.LENGTH_LONG).show()

        );
    }


}

package mykyta.nechyporenko.pocket_crib_v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void SendMsg(View view){
        Intent intent;
        intent=new Intent(this,Catalogue_Activity.class);
        EditText NAME = (EditText) findViewById(R.id.name);
        String name= String.valueOf(NAME.getText());

        EditText ID = (EditText) findViewById(R.id.ID);
        String id= String.valueOf(ID.getText());

        EditText PHONE = (EditText) findViewById(R.id.Phone);
        String phone = String.valueOf(ID.getText());

        if(name.equals("") || id.equals("") || phone.equals("")) {
            //startActivity(intent);
            Context context = getApplicationContext();
            CharSequence text = "Information fields must be filled";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
           /* Toast toast = Toast.makeText(getApplicationContext(),
                    "@string/loginError",
                    Toast.LENGTH_SHORT);
            toast.show();*/
           /* Context context = getApplicationContext();
            CharSequence text = "Information fields must be filled";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/
            startActivity(intent);
        }
    }

}
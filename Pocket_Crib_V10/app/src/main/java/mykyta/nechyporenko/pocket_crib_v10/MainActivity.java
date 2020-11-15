package mykyta.nechyporenko.pocket_crib_v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void SendMsg(View view){
        Intent intent;
        intent=new Intent(this,Catalogue_Activity.class);
        startActivity(intent);
    }

}
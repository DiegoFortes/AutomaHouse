package com.example.automahouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Lampada extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    protected void faz_algo(int lampId, int lamp_img_id) {
        database = FirebaseDatabase.getInstance();
        Switch lamp_switch = findViewById(lampId);
        String lampName  = lamp_switch.getText().toString();
        myRef = database.getReference(lampName);
        ImageView img_switch = findViewById(lamp_img_id);
        img_switch.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_off));
        lamp_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lamp_switch.isChecked()) {
                    img_switch.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_on));
                    myRef.setValue(1);
                } else {
                    img_switch.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_off));
                    myRef.setValue(0);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lampada);

        faz_algo(R.id.lamp1, R.id.ic_lamp1);



        Button btnLogout = (Button) findViewById(R.id.logout1);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lampada.this, MainActivity.class);
                startActivity( intent );
            }
        });
    }
}

package com.example.automahouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Lampada extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lampada);

        Switch switch1 = findViewById(R.id.lamp1);
        Switch switch2 = findViewById(R.id.lamp2);
        Switch switch3 = findViewById(R.id.lamp3);
        Switch switch4 = findViewById(R.id.lamp4);
        Switch switch5 = findViewById(R.id.lamp5);

        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);
        switch3.setOnClickListener(this);
        switch4.setOnClickListener(this);
        switch5.setOnClickListener(this);

        Button btnLogout = (Button) findViewById(R.id.logout1);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lampada.this, MainActivity.class);
                startActivity( intent );
            }
        });
    }

    protected void activateSwitch(Switch lamp, ImageView icon, DatabaseReference dbRef) {
//                Toast.makeText(this,"Botão 1",Toast.LENGTH_SHORT).show();
        if (lamp.isChecked()) {
            icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_on));
            dbRef.setValue(1);
        } else {
            icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_off));
            dbRef.setValue(0);
        }
    }

    @Override
    public void onClick(View v) {
        database = FirebaseDatabase.getInstance();
        Switch lampSwitch = findViewById(v.getId());
        String lampName = lampSwitch.getText().toString();
        myRef = database.getReference(lampName);
        ImageView imgSwitch = null;
        switch (v.getId()) {
            case R.id.lamp1:
                imgSwitch = findViewById(R.id.ic_lamp1);
                break;
            case R.id.lamp2:
                imgSwitch = findViewById(R.id.ic_lamp2);
                break;
            case R.id.lamp3:
                imgSwitch = findViewById(R.id.ic_lamp3);
                break;
            case R.id.lamp4:
                imgSwitch = findViewById(R.id.ic_lamp4);
                break;
            case R.id.lamp5:
                imgSwitch = findViewById(R.id.ic_lamp5);
                break;
        }
        activateSwitch(lampSwitch, imgSwitch, myRef);
    }
}

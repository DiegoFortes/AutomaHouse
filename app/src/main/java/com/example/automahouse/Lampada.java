package com.example.automahouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


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

        // mapeia nome da lâmpada para id do switch
        HashMap<String, Integer> switchIdsByText = new HashMap<>();
        switchIdsByText.put(switch1.getText().toString(), R.id.lamp1);
        switchIdsByText.put(switch2.getText().toString(), R.id.lamp2);
        switchIdsByText.put(switch3.getText().toString(), R.id.lamp3);
        switchIdsByText.put(switch4.getText().toString(), R.id.lamp4);
        switchIdsByText.put(switch5.getText().toString(), R.id.lamp5);

        // mapeia id do switch para id da imagem
        HashMap<Integer, Integer> imageIdsBySwitchId = new HashMap<>();
        imageIdsBySwitchId.put(R.id.lamp1, R.id.ic_lamp1);
        imageIdsBySwitchId.put(R.id.lamp2, R.id.ic_lamp2);
        imageIdsBySwitchId.put(R.id.lamp3, R.id.ic_lamp3);
        imageIdsBySwitchId.put(R.id.lamp4, R.id.ic_lamp4);
        imageIdsBySwitchId.put(R.id.lamp5, R.id.ic_lamp5);

        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);
        switch3.setOnClickListener(this);
        switch4.setOnClickListener(this);
        switch5.setOnClickListener(this);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> lamps = dataSnapshot.getChildren();
                for (DataSnapshot lamp : lamps) {
                    Integer isOn = lamp.getValue(Integer.class);
                    String lampName = lamp.getKey();
                    Integer lampId = switchIdsByText.get(lampName);
                    Switch lampSwitch = findViewById(lampId);
                    if (lampSwitch.isChecked() ^ isOn.equals(1)) {
                        ImageView icon = findViewById(imageIdsBySwitchId.get(lampId));
//                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_on));
                        lampSwitch.setChecked(isOn.equals(1));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error getting data from DB.");
            }
        };
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.addValueEventListener(postListener);

        Button btnLogout = (Button) findViewById(R.id.logout1);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lampada.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void activateSwitch(Switch lamp, ImageView icon, DatabaseReference dbRef) {
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

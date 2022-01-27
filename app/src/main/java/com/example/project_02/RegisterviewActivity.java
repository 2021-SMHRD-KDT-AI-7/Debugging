package com.example.project_02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterviewActivity extends AppCompatActivity {

    Button registerBtn;
    EditText idet, pwet, nameet, bdet, adminet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("ORACLE");

        registerBtn = (Button) findViewById(R.id.register_btn);
        idet = (EditText) findViewById(R.id.register_id);
        pwet = (EditText) findViewById(R.id.register_pw);
        nameet = (EditText) findViewById(R.id.register_name);
        bdet = (EditText) findViewById(R.id.register_bd);
        adminet = (EditText) findViewById(R.id.register_admin);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String result;
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();
                    String name = nameet.getText().toString();
                    String bd = bdet.getText().toString();
                    String ad = adminet.getText().toString();


                    RegisterActivity task = new RegisterActivity();
                    result = task.execute(id, pw, name, bd, ad).get();
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

    }
}
package com.myapp.karekaro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.karekaro.Models.User;
import com.myapp.karekaro.R;

import java.util.Objects;



public class Send_otp extends AppCompatActivity {

    FirebaseAuth mAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        String URL = "http://192.168.1.5/KareKaro/mobilenumber.php?mo_number=";

        EditText edttxt = findViewById(R.id.mobilenumber);
        Button btsendotp = findViewById(R.id.btn_getotp);

        btsendotp.setOnClickListener(view -> {
            String MobileNumber = Objects.requireNonNull(edttxt.getText()).toString().trim();
            RequestQueue queue = Volley.newRequestQueue(this);
            if(MobileNumber.length() != 10){
                Toast.makeText(this,"Enter valid Mobile Number",Toast.LENGTH_SHORT).show();
            }else{
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + MobileNumber,
                        response -> {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            User user = gson.fromJson(response,User.class);
                            if(Objects.equals(user.getMobilenumber(), MobileNumber)){
                                Intent intent = new Intent(Send_otp.this, Verify_otp.class);
                                intent.putExtra("MobileNumber",MobileNumber);
                                startActivity(intent);

                            }else{Toast.makeText(this,"Contact Admin",Toast.LENGTH_SHORT).show();}
                            }, error -> Toast.makeText(this,"Mobile Number is not Verified.",Toast.LENGTH_SHORT).show());

                queue.add(stringRequest);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(Send_otp.this,Next.class);
            startActivity(intent);
            finish();
        }
    }
}
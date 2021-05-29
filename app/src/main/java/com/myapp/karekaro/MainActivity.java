package com.myapp.karekaro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button btsendotp;
    private TextInputEditText edttxt;
    public FirebaseAuth mAuth;
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttxt = findViewById(R.id.mobilenumber);
        btsendotp = findViewById(R.id.btn_getotp);

        btsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edttxt.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter Mobile",Toast.LENGTH_SHORT).show();
                    return;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + edttxt.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        MainActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Intent intent = new Intent(getApplicationContext(), Verify_OTP.class);
                                intent.putExtra("mobile",edttxt.getText().toString());
                                intent.putExtra("Verification",verificationID);
                                startActivity(intent);
                            }
                        }
                );


            }
        });
    }

}
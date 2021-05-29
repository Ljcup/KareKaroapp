package com.myapp.karekaro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_OTP extends AppCompatActivity {

    private EditText inotp1;
    private EditText inotp2;
    private EditText inotp3;
    private EditText inotp4;
    private EditText inotp5;
    private EditText inotp6;
    private Button btverify;
    private String verificationID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__o_t_p);

        inotp1 = (EditText) findViewById(R.id.inotp1);
        inotp2 = (EditText) findViewById(R.id.inotp2);
        inotp3 = (EditText) findViewById(R.id.inotp3);
        inotp4 = (EditText) findViewById(R.id.inotp4);
        inotp5 = (EditText) findViewById(R.id.inotp5);
        inotp6 = (EditText) findViewById(R.id.inotp6);
        btverify = (Button) findViewById(R.id.btn_veriyotp);

        setOTPinput();
        verificationID = getIntent().getStringExtra("VerificationId");

        btverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inotp1.getText().toString().trim().isEmpty()
                        ||inotp2.getText().toString().trim().isEmpty()
                        ||inotp3.getText().toString().trim().isEmpty()
                        ||inotp4.getText().toString().trim().isEmpty()
                        ||inotp5.getText().toString().trim().isEmpty()
                        ||inotp6.getText().toString().trim().isEmpty()){
                    Toast.makeText(Verify_OTP.this,"Please enter valid code",Toast.LENGTH_SHORT).show();
                    return;
                }
                String code =
                        inotp1.getText().toString() +
                                inotp2.getText().toString() +
                                inotp3.getText().toString() +
                                inotp4.getText().toString() +
                                inotp5.getText().toString() +
                                inotp6.getText().toString();

                if (verificationID != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationID,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), Next.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(Verify_OTP.this,"Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });;
                }
            }
        });

        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        Verify_OTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Verify_OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationID = newVerificationID;
                                Toast.makeText(Verify_OTP.this,"OTp Sent",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("OTP Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

        private void setOTPinput(){
            inotp1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().isEmpty()){
                        inotp2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            inotp2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().isEmpty()){
                        inotp3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            inotp3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().isEmpty()){
                        inotp4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            inotp4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().isEmpty()){
                        inotp5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            inotp5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().isEmpty()){
                        inotp6.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


    }
}
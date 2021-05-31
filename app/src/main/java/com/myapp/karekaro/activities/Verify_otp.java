package com.myapp.karekaro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.myapp.karekaro.R;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Verify_otp extends AppCompatActivity {

    private EditText inotp1;
    private EditText inotp2;
    private EditText inotp3;
    private EditText inotp4;
    private EditText inotp5;
    private EditText inotp6;
    private TextView resendotp;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    String VerificationId;
    String MobileNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        inotp1 = findViewById(R.id.inotp1);
        inotp2 = findViewById(R.id.inotp2);
        inotp3 = findViewById(R.id.inotp3);
        inotp4 = findViewById(R.id.inotp4);
        inotp5 = findViewById(R.id.inotp5);
        inotp6 = findViewById(R.id.inotp6);
        resendotp = findViewById(R.id.resendotp);
        Button btverify = findViewById(R.id.btn_getotp);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("OTP Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String otp = phoneAuthCredential.getSmsCode();
                if (otp != null) {
//                    autoOTP(otp);
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Verify_otp.this, "Verification Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(Verify_otp.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                VerificationId = s;
                mResendToken = forceResendingToken;
            }
        };
        setOTPinput();
        MobileNumber = getIntent().getStringExtra("MobileNumber");
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + MobileNumber)
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

        btverify.setOnClickListener(v -> {
            if (isEmptyinput()) {
                Toast.makeText(Verify_otp.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
            } else {
                String code = getOTPinput();
                verifyOtp(code);
            }
        });
    }

    private void autoOTP(String code) {
        inotp1.setText(code.charAt(0));
        inotp2.setText(code.charAt(1));
        inotp3.setText(code.charAt(2));
        inotp4.setText(code.charAt(3));
        inotp5.setText(code.charAt(4));
        inotp6.setText(code.charAt(5));
    }

    private void resendVerificationCode(String mobileNumber,
                                        PhoneAuthProvider.ForceResendingToken token){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobileNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Verify_otp.this, Next.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Verify_otp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isEmptyinput() {
        return inotp1.getText().toString().trim().isEmpty()
                || inotp2.getText().toString().trim().isEmpty()
                || inotp3.getText().toString().trim().isEmpty()
                || inotp4.getText().toString().trim().isEmpty()
                || inotp5.getText().toString().trim().isEmpty()
                || inotp6.getText().toString().trim().isEmpty();
    }

    private void setOTPinput() {
        inotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
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
                if (!charSequence.toString().trim().isEmpty()) {
                    inotp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private String getOTPinput() {
        if (!isEmptyinput()) {
            return inotp1.getText().toString() +
                    inotp2.getText().toString() +
                    inotp3.getText().toString() +
                    inotp4.getText().toString() +
                    inotp5.getText().toString() +
                    inotp6.getText().toString();
        } else {
            return "";
        }
    }

    private void verifyOtp(String otp) {
        if (VerificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId, otp);
            signInWithPhoneAuthCredential(credential);
        } else {
            Log.d("VerificationID error", "Error");
        }
    }

    private void timerOtp(){
        new CountDownTimer(90000,1000){
            @Override
            public void onTick(long l) {
                int min = (int) (l/1000)/60;
                int sec = (int) (l/1000)-(min*60);
                DecimalFormat formatter = new DecimalFormat("00");
                resendotp.setText("Resend OTP ("+formatter.format(min)+":"+formatter.format(sec)+")");
            }

            @Override
            public void onFinish() {
                resendotp.setText("Resend OTP.");
                resendotp.setOnClickListener(view -> {
                    resendVerificationCode(VerificationId,mResendToken);
                });
            }
        }.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timerOtp();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(Verify_otp.this, Next.class);
            startActivity(intent);
            finish();
        }
    }
}
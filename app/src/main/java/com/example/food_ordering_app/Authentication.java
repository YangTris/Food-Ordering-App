package com.example.food_ordering_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity {

    TextInputEditText editTextVerificationCode, editTextPhone;
    Button buttonSendVerificationCode, buttonVerify;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();

        editTextPhone = findViewById(R.id.phone);
        editTextVerificationCode = findViewById(R.id.code);
        buttonSendVerificationCode = findViewById(R.id.buttonSendVerificationCode);
        buttonVerify = findViewById(R.id.buttonVerify);

        // Initialize callbacks for phone verification
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the phone number format is not valid.
                Toast.makeText(Authentication.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // The SMS verification code has been sent to the provided phone number.
                // You need to save the verification ID and the resending token to use them later
                mVerificationId = verificationId;
            }
        };

        buttonSendVerificationCode.setOnClickListener(v -> {
            //84 is VN country code
            String phoneNumber = "+84" + editTextPhone.getText().toString();
            startPhoneNumberVerification(phoneNumber);
        });

        buttonVerify.setOnClickListener(v -> {
            String verificationCode = editTextVerificationCode.getText().toString();
            verifyPhoneNumberWithCode(verificationCode);
        });
    }

    public void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
        Intent intent = new Intent(Authentication.this, RegisterActivity.class);
        intent.putExtra("phoneNumber", editTextPhone.getText().toString());
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success
                Toast.makeText(Authentication.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                FirebaseUser user = task.getResult().getUser();
            } else {
                // Sign in failed
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid verification code
                    Toast.makeText(Authentication.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

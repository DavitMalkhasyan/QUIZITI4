package com.example.quiziti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText name, email, password;
    TextView alreadyAccount;

    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnSignUp = findViewById(R.id.button);
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPersonName4);

        alreadyAccount = findViewById(R.id.textView2);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Creating Your Account");
        progressDialog.setMessage("Your Account Is Creating");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtName = name.getText().toString();
                String edtEmail = email.getText().toString();
                String edtPassword = password.getText().toString();

                auth.createUserWithEmailAndPassword(edtEmail, edtPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    UserModel model = new UserModel(edtName, edtEmail, edtPassword);
                                    String id = task.getResult().getUser().getUid();

                                    database.getReference().child("Users").child(id).setPriority(model);


                                }

                            }
                        });

            }
        });
    }
}
package com.example.logindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText password;
    private Button Login;
    private TextView Ltext;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.lemail);
        password = (EditText)findViewById(R.id.lpassword);
        Login = (Button)findViewById(R.id.lbutton);
        Ltext=(TextView)findViewById(R.id.ltext);

        firebaseAuth = firebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,secondactivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String N = Name.getText().toString();
                String P = password.getText().toString();
                if(N.isEmpty() || P.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    validate(N,P);
                }

            }
        });

        Ltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });


    }
    public void checkemailandpassword()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean Email = firebaseUser.isEmailVerified();
        if(Email)
        {
            startActivity(new Intent(MainActivity.this,secondactivity.class));
        }
        else
        {
            Toast.makeText(MainActivity.this,"Verify your email",Toast.LENGTH_SHORT).show();
        }
    }
    private void validate(String username,String userpassword)
    {
        firebaseAuth.signInWithEmailAndPassword(username,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    checkemailandpassword();
                    //Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                }
            }
        });
    }

}

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


public class Registration extends AppCompatActivity {

    private EditText Rname, Rpassword, REmail, RPhone;
    private Button Rbutton;
    private TextView Rtext;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setup();

        firebaseAuth = FirebaseAuth.getInstance();


        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate())
               {
                   String email = REmail.getText().toString().trim();
                   String password=Rpassword.getText().toString().trim();

                   firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                               {
                                   Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(Registration.this,MainActivity.class));
                               }
                           else
                               {
                                   Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                               }
                       }
                   });
               }

            }
        });

        Rtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Registration.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }


    private void setup() {
        Rname = (EditText) findViewById(R.id.rname);
        Rpassword = (EditText) findViewById(R.id.rpassword);
        REmail = (EditText) findViewById(R.id.remail);
        RPhone = (EditText) findViewById(R.id.rphone);
        Rbutton = (Button) findViewById(R.id.rbutton);
        Rtext = (TextView) findViewById(R.id.rtext);
    }
    private void checkemailandpassword()
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

    private void sendemail()
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Registration.this,"Verification Email sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Registration.this,"Failed to send Verification Email",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private Boolean validate() {
        Boolean result = false;

        String Name = Rname.getText().toString();
        String Password = Rpassword.getText().toString();
        String Email = REmail.getText().toString();
        String Phone = RPhone.getText().toString();

        if (Name.isEmpty() || Password.isEmpty() || Email.isEmpty() || Phone.isEmpty())
            Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show();
        else {
            result = true;
        }
        return result;
    }
}
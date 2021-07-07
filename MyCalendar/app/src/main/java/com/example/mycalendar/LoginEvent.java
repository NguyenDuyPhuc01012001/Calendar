package com.example.mycalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycalendar.model.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginEvent extends AppCompatActivity{

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView error;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String txt_email;
    private String txt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_event);
        email = findViewById(R.id.emailLogET);
        password = findViewById(R.id.passwordLogET);
        loginBtn = findViewById(R.id.LoginBtn);
        error = findViewById(R.id.errorText);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(this,OnlineEvent.class));
            Toast.makeText(this,"đăng nhập thành công!",Toast.LENGTH_SHORT).show();
            finish();
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email = email.getText().toString().trim();
                txt_password = password.getText().toString().trim();
                Login login = new Login("",txt_email,txt_password);
                if(login.isEmailValid() && login.isPasswordValid())
                {
                    loginUser(txt_email,txt_password);
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(LoginEvent.this,"Email và password không hợp lệ!",Toast.LENGTH_SHORT).show();
                    return;
                }

            }


        });
    }

    public void registerOnClick(View view) {
        Intent intent = new Intent(this,RegisterEvent.class);
        startActivity(intent);
        finish();
    }
    private void loginUser(String txt_email, String txt_password) {

        auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginEvent.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginEvent.this,OnlineEvent.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LoginEvent.this,"Email hoặc password không tồn tại!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

}
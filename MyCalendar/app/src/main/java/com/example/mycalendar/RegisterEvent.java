package com.example.mycalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycalendar.model.Login;
import com.example.mycalendar.presenter.RegisterInterface;
import com.example.mycalendar.presenter.RegisterPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegisterEvent extends AppCompatActivity implements RegisterInterface {

    private EditText Name;
    private EditText Email;
    private EditText Password;
    private TextView Error;
    private Button registerbtn;
    private String name;
    private String email;
    private String password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);
        Name = findViewById(R.id.nameET);
        Email = findViewById(R.id.EmailET);
        Password = findViewById(R.id.passwordET);
        registerbtn = findViewById(R.id.RegisterBtn);
        Error = findViewById(R.id.errorText);
        auth = FirebaseAuth.getInstance();
        RegisterPresenter registerPresenter = new RegisterPresenter(this);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();
                Login login = new Login(name,email,password);
                registerPresenter.RegisterCheck(login);
            }
        });
    }

    @Override
    public void RegisterError() {
        Error.setText("Email hoặc password không hợp lệ");
        Error.setVisibility(View.VISIBLE);
    }

    @Override
    public void RegisterSuccess() {
        registeUser(name,email,password);
    }

    private void registeUser(String name,String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterEvent.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterEvent.this,"Tạo tài khoản thành công!",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(RegisterEvent.this,"Lỗi hệ thống",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
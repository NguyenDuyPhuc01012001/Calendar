package com.example.mycalendar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mycalendar.R;
import com.example.mycalendar.model.Login;
import com.example.mycalendar.presenter.RegisterInterface;
import com.example.mycalendar.presenter.RegisterPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterEvent extends AppCompatActivity implements RegisterInterface {

    private EditText Name;
    private EditText Email;
    private EditText Password;
    private Button registerbtn;
    private String name;
    private String email;
    private String password;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String UserID;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);
        Name = findViewById(R.id.nameET);
        Email = findViewById(R.id.EmailET);
        Password = findViewById(R.id.passwordET);
        registerbtn = findViewById(R.id.RegisterBtn);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.registerPB);
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

        Toast.makeText(RegisterEvent.this,"Email hoặc password không hợp lệ!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterSuccess() {
        registeUser(name,email,password);
    }

    private void registeUser(String name,String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterEvent.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterEvent.this,"Tạo tài khoản thành công, vui lòng kiểm tra email để xác thực",Toast.LENGTH_SHORT).show();
                                UserID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(UserID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("Name",name);
                                user.put("email",email);
                                user.put("Password",password);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("User profile","created successfully");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Log.i("User profile","cannot insert user profile");
                                    }
                                });
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterEvent.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {

                    Toast.makeText(RegisterEvent.this,"Người dùng đã tồn tại hoặc lỗi kết nối",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
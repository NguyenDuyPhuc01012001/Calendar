package com.example.mycalendar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycalendar.R;
import com.example.mycalendar.model.Login;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class LoginEvent extends AppCompatActivity{

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView error;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String txt_email;
    private String txt_password;
    private FirebaseUser user;

    private String TAG = "FACEBOOK LOGIN";
    private CallbackManager mCallbackManager;
    private Button FBloginButton;

    private String GGTAG = "GOOGLE LOGIN";
    private Button GoogleLoginButton;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_event);
        //init Facebook sdk
        FacebookSdk.sdkInitialize(LoginEvent.this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        FBloginButton = findViewById(R.id.fb_login_button);
        FBloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginEvent.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
//                        mProgressbarAuth.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });

            }
        });
//        FBloginButton.setReadPermissions("email", "public_profile");
//        FBloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//            }
//        });


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleLoginButton = findViewById(R.id.google_sign_in_button);
        GoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });


        email = findViewById(R.id.emailLogET);
        password = findViewById(R.id.passwordLogET);
        loginBtn = findViewById(R.id.LoginBtn);
        error = findViewById(R.id.errorText);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        try{
            if((user != null && user.isEmailVerified()) || user.getDisplayName() != "" )
            {
                startActivityForResult(new Intent(this, OnlineEvent.class),10001);
                Toast.makeText(this,"đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
            }
        }
        catch (Exception e)
        {
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
        Intent intent = new Intent(this, RegisterEvent.class);
        startActivity(intent);
    }
    private void loginUser(String txt_email, String txt_password) {

        auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(auth.getCurrentUser().isEmailVerified())
                    {
                        Toast.makeText(LoginEvent.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginEvent.this,OnlineEvent.class));
                    }
                    else
                    {
                        Toast.makeText(LoginEvent.this,"Vui lòng xác thực tài khoản trên email của bạn",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(LoginEvent.this,"Email hoặc password không tồn tại!",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });


    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LoginEvent.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(LoginEvent.this,OnlineEvent.class),10001);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginEvent.this, "Email đã tồn tại hoặc lỗi hệ thống",
                                    Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_SIGN_IN)
        {
            Log.d(GGTAG,"onActivityResult: google SigninResult");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                //google sign in success
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                FirebaseAuthwithGoogleAccount(account);
            } catch (Exception e) {
                Log.d(GGTAG,"onActivityResult: " + e.getMessage());
                Toast.makeText(this,"Lỗi đăng nhập!",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 10001)
        {
            setResult(Activity.RESULT_OK);
            finish();
        }
        else
        {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void FirebaseAuthwithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginEvent.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(LoginEvent.this,OnlineEvent.class),10001);
                }
                else
                {
                    Log.i(GGTAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginEvent.this, "Email đã tồn tại hoặc lỗi hệ thống",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void forgotOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginEvent.this,R.style.AlertDialog);
        final View forgotDialog = getLayoutInflater().inflate(R.layout.dialog_forgot_password,null);
        builder.setView(forgotDialog);
        builder.setTitle("Quên mật khẩu");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Gửi email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                EditText forgotPassword = forgotDialog.findViewById(R.id.forgotET);
                auth.sendPasswordResetEmail(forgotPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginEvent.this,"đã gửi thông báo đến email của bạn!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginEvent.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
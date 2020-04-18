package com.everaldo.kudi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.everaldo.kudi2.util.Crypt;
import com.everaldo.kudi2.util.LoginListener;
import com.everaldo.kudi2.util.ResponseResult;
import com.everaldo.kudi2.util.LoginRequest;

public class MainActivity extends AppCompatActivity implements LoginListener{

    private TextView txtCreateAccount;
    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCreateAccount = findViewById(R.id.txtCreateAccount);

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Create Account", Toast.LENGTH_LONG).show();

                //RegisterDialog customDialog =new RegisterDialog(MainActivity.this);
                //customDialog .show();

                startActivity( new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);

        editUsername = findViewById(R.id.txtUsername);
        editPassword = findViewById(R.id.txtPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String password = Crypt.sha1(editPassword.getText().toString());
                    LoginRequest serv = new LoginRequest(MainActivity.this);
                    serv.performLogin(MainActivity.this, editUsername.getText().toString(), password);
                }
                catch(Exception ex){

                }
            }
        });

    }

    @Override
    public void loginFinish(ResponseResult loginResult) {
        if (loginResult.isOperationSuccessful()) {
            finish();
            Toast.makeText(MainActivity.this, loginResult.getUser().toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(), HomeMainActivity.class);
            startActivity(intent);

        }
        else
            Toast.makeText(MainActivity.this, "Username or password is incorrect", Toast.LENGTH_LONG).show();
    }
}

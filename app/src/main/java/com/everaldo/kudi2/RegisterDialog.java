package com.everaldo.kudi2;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everaldo.kudi2.util.Crypt;
import com.everaldo.kudi2.util.RegisterRequest;
import com.everaldo.kudi2.util.RegistrationListener;
import com.everaldo.kudi2.util.ResponseResult;
import com.everaldo.kudi2.util.User;


public class RegisterDialog extends Dialog implements RegistrationListener {

    public Activity activity;
    public Button btnRegister;
    public Button btnCancel;
    private EditText txtPhone;
    private EditText txtEmail;
    private EditText txtPassword1;
    private EditText txtPassword2;

    public RegisterDialog(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_dialog);

        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);

        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword1 = findViewById(R.id.txtPassword1);
        txtPassword2 = findViewById(R.id.txtPassword2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final String phone = txtPhone.getText().toString();
                final String email = txtEmail.getText().toString();
                final String password1 = txtPassword1.getText().toString();
                final String password2 = txtPassword2.getText().toString();
                String encryptedPassword = null;


                //boolean allOK = true;
                //Toast.makeText(activity, "Name :" + txtPhone.getText(), Toast.LENGTH_LONG).show();
                if (phone.matches("^9[65]\\d{7}$") && !email.isEmpty() && password1.equals(password2)) {
                    ((EditText) activity.findViewById(R.id.txtUsername)).setText(txtPhone.getText());
                    ((EditText) activity.findViewById(R.id.txtPassword)).setText(txtPassword1.getText());

                    try{
                        encryptedPassword = Crypt.sha1(password1);
                    }
                    catch(Exception ex){

                    }

                    RegisterRequest req = new RegisterRequest(activity);
                    req.performRegistration(RegisterDialog.this, new User(phone, email, encryptedPassword));

                }
                else{
                    Toast.makeText(activity, "Invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //activity.finish();
                RegisterDialog.this.dismiss();
            }
        });

    }

    @Override
    public void registrationDone(ResponseResult result) {
        if (result.isOperationSuccessful()){
            Toast.makeText(activity, result.getUser().toString(), Toast.LENGTH_LONG).show();
            this.dismiss();
        }
    }
}
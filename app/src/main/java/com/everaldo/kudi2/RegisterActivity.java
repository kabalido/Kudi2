package com.everaldo.kudi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.everaldo.kudi2.Fragments.FragmentStep2;
import com.everaldo.kudi2.util.Crypt;
import com.everaldo.kudi2.util.RegisterRequest;
import com.everaldo.kudi2.util.RegistrationListener;
import com.everaldo.kudi2.util.ResponseResult;
import com.everaldo.kudi2.util.User;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements RegistrationListener {

    private FragmentStep1 fragment1;
    private FragmentStep2 fragment2;
    private EditText edPhone;
    private EditText edName;
    private Button btnNext;
    private Button btnPrevious;
    private int step = 0;
    private HashMap<String, String> infoMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fragment1 = new FragmentStep1();
        fragment2 = new FragmentStep2();

        infoMap = new HashMap<>();

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fl_main_content, fragment1);
        trans.commit();


        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);
        btnPrevious.setVisibility(View.GONE);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fl_main_content, fragment1);
                trans.commit();
                btnNext.setText("Seguinte");
                btnPrevious.setVisibility(View.GONE);
                step = 0;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step == 0) {
                    EditText edPhone = findViewById(R.id.et_nr_phone);
                    //String phone = edPhone.getText().toString();
                    EditText etEmail = findViewById(R.id.et_email);
                    EditText etPassword1 = findViewById(R.id.et_password1);
                    EditText etPassword2 = findViewById(R.id.et_password2);

                    infoMap.put("phone", edPhone.getText().toString());
                    infoMap.put("email", etEmail.getText().toString());
                    infoMap.put("password1", etPassword1.getText().toString());
                    infoMap.put("password2", etPassword2.getText().toString());


                    Toast.makeText(RegisterActivity.this, edPhone.getText().toString(), Toast.LENGTH_SHORT).show();

                    //edPhone
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.fl_main_content, fragment2);
                    trans.commit();
                    btnNext.setText("Registrar");
                    step =1;
                    btnPrevious.setVisibility(View.VISIBLE);
                }
                else{
                    EditText etName = findViewById(R.id.et_name);
                    EditText etAddress = findViewById(R.id.et_address);
                    Spinner spIdType = findViewById(R.id.sp_id_type);
                    EditText etIdCard = findViewById(R.id.et_nr_idcard);
                    EditText etIdCardValidity = findViewById(R.id.et_validity);
                    infoMap.put("name", etName.getText().toString());
                    infoMap.put("address", etAddress.getText().toString());
                    infoMap.put("cardIdType", spIdType.getSelectedItem().toString().split("-")[0]);
                    infoMap.put("cardId", etIdCard.getText().toString());
                    infoMap.put("idValidity", etIdCardValidity.getText().toString());
                    registerUser();
                    //Toast.makeText(RegisterActivity.this, "Registrado com success " + edPhone.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerUser(){

        String encryptedPassword = null;
        String phone = infoMap.get("phone");
        String email = infoMap.get("email");
        String password1 = infoMap.get("password1");
        String password2 = infoMap.get("password2");
        String name = infoMap.get("name");
        String address = infoMap.get("address");
        String cardIdType = infoMap.get("cardIdType");
        String cardId = infoMap.get("cardId");
        String idValidity = infoMap.get("idValidity");

        Log.i("COCO", phone + ";" + email + ";" + password1 + ";" + password2);
        if (phone.matches("^9[65]\\d{7}$") && !email.isEmpty() && password1.equals(password2)) {

            try{
                encryptedPassword = Crypt.sha1(password1);
            }
            catch(Exception ex){

            }
            User user = new User(phone,email, encryptedPassword);
            user.setName(name);
            user.setAddress(address);
            user.setIdCardType(cardIdType);
            user.setIdCard(cardId);
            user.setIdCardValidity(idValidity);
            RegisterRequest req = new RegisterRequest(this);
            req.performRegistration(this, user);

        }
        else{
            Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void registrationDone(ResponseResult result) {
        if (result.isOperationSuccessful()){
            Toast.makeText(this, result.getUser().toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else{
            Toast.makeText(this, "Error:" + result.getStatus() + ";" + result.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

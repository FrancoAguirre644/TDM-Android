package com.example.tdm_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdm_android.R;
import com.example.tdm_android.constants.Constants;
import com.example.tdm_android.managers.UserManager;
import com.example.tdm_android.models.User;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin;
    Button btnRegister;
    EditText etUsername, etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeVariables();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToLogin(view, null, false);
            }
        });

    }

    public void redirectToLogin(View view, String strUsername, boolean sendUsername){
        Intent intent = new Intent(this, LoginActivity.class);
        if(sendUsername){
            intent.putExtra(Constants.STR_KEY_USERNAME, strUsername);
        }
        startActivity(intent);
    }

    public void register(View view){
        String strUsername, strPassword, strEmail;
        strUsername = etUsername.getText().toString();
        strPassword = etPassword.getText().toString();
        strEmail = etEmail.getText().toString();
        if (isFieldsAreCorrect_or_returnsFalseAndMessage(strUsername, strPassword, strEmail)){
            createUser(strUsername, strEmail, strPassword);
            redirectToLogin(view, strUsername, true);
        }
    }

    public boolean isFieldsAreCorrect_or_returnsFalseAndMessage(String strUsername, String strPassword, String strEmail){

        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        TextView tvDialogInfo = (TextView) dialog.findViewById(R.id.tvDialogInfo);
        Button btnDialogOk = (Button) dialog.findViewById(R.id.btnDialogOk);

        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        boolean isFieldsCorrect = false;
        if (strUsername.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty() || etConfirmPassword.getText().toString().isEmpty()){
            tvDialogInfo.setText(Constants.COMPLETE_ALL_FIELD_MESSAGE);
            dialog.show();
            //Toast.makeText(this, Constants.COMPLETE_ALL_FIELD_MESSAGE, Toast.LENGTH_SHORT).show();
        }else {
            if ( !strPassword.equals(etConfirmPassword.getText().toString()) ) {
                tvDialogInfo.setText(Constants.PASSWORD_CONFIRMATION_ERROR_MESSAGE);
                dialog.show();
                //Toast.makeText(this, Constants.PASSWORD_CONFIRMATION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            }else{
                if(isTheValueInTheFieldExists("username", strUsername)){
                    tvDialogInfo.setText(Constants.USERNAME_ALREADY_REGISTERED_MESSAGE);
                    dialog.show();
                    //Toast.makeText(this, Constants.USERNAME_ALREADY_REGISTERED_MESSAGE, Toast.LENGTH_SHORT).show();
                } else if(isTheValueInTheFieldExists("email", strEmail)){
                    tvDialogInfo.setText(Constants.EMAIL_ALREADY_REGISTERED_MESSAGE);
                    dialog.show();
                    //Toast.makeText(this, Constants.EMAIL_ALREADY_REGISTERED_MESSAGE, Toast.LENGTH_SHORT).show();
                } else {
                    isFieldsCorrect = true;
                }
            }
        }
        return isFieldsCorrect;
    }

    public void createUser(String strUsername, String strEmail, String strPassword) {
        try{
            UserManager.getInstance(RegisterActivity.this).createUser(new User(strUsername, strEmail, strPassword));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isTheValueInTheFieldExists(String nameField, String valueField){
        User user = null;
        boolean isExistValue = false;
        try {
            user = UserManager.getInstance(RegisterActivity.this).getOneUserByField(nameField, valueField);
            if ( user != null) {
                isExistValue = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExistValue;
    }

    private void initializeVariables() {
        btnRegister= findViewById(R.id.register);
        tvLogin= findViewById(R.id.tvLogin);
        etUsername = findViewById(R.id.txtNameUser);
        etEmail = findViewById(R.id.txtEmail);
        etPassword = findViewById(R.id.txtPassword);
        etConfirmPassword = findViewById(R.id.txtConfirmPassword);
    }

}
package com.example.tdm_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdm_android.R;
import com.example.tdm_android.constants.Constants;
import com.example.tdm_android.managers.UserManager;
import com.example.tdm_android.models.User;

public class LoginActivity extends AppCompatActivity {

    TextView tvCreateUser;
    EditText etUsername, etPassword;
    Button btnLogin;
    CheckBox checkRememberUser;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String strName = getIntent().getStringExtra(Constants.STR_KEY_USERNAME);
        if(strName != null) { //Si se acaba de registrar
            Toast.makeText(this, Constants.REGISTERED_SUCCESSFULLY_MESSAGE + strName, Toast.LENGTH_SHORT).show();
        }

        initializeVariables();

        if (this.pref.getBoolean(Constants.STR_CHECK_REMEMBER_USER, false)) {//Si había elegido recordar usuario
            redirectToFilterActivity();
        }

        tvCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToCreateUser(view);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

    }

    public void redirectToCreateUser(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view){

        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();

        final Dialog dialog = new Dialog(LoginActivity.this);
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

        if ( strUsername.isEmpty() || strPassword.isEmpty()){
            tvDialogInfo.setText(Constants.COMPLETE_ALL_FIELD_MESSAGE);
            dialog.show();
        }else if( isTheCredentialsAreValid(strUsername, strPassword) ) {
            saveDataUser(checkRememberUser.isChecked());
            redirectToFilterActivity();
        }else{
            tvDialogInfo.setText(Constants.INVALID_CREDENTIALS_MESSAGE);
            dialog.show();
        }
    }

    public void redirectToFilterActivity(){
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra(Constants.STR_KEY_USERNAME, etUsername.getText().toString());
        startActivity(intent);
    }
    public boolean isTheCredentialsAreValid(String strUsername, String strPassword){
        try {
            User user = UserManager.getInstance(LoginActivity.this).getOneUserByField("username", strUsername);
            if (user != null && user.getPassword().equals(strPassword)) {return true;}
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void initializeVariables() {
        tvCreateUser = findViewById(R.id.createUser);
        btnLogin = findViewById(R.id.login);
        etUsername = findViewById(R.id.txtNameUser);
        etPassword = findViewById(R.id.txtPassword);
        checkRememberUser = findViewById(R.id.checkRememberUser);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();
    }

    public void saveDataUser(boolean isRememberUser){
        editor.putString(Constants.STR_USERNAME, etUsername.getText().toString());
        editor.putString(Constants.STR_PASSWORD, etPassword.getText().toString());
        editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, isRememberUser);
        editor.apply();
    }

}
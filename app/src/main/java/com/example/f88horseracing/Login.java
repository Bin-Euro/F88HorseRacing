package com.example.f88horseracing;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Login extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button ruleButton;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        ruleButton = findViewById(R.id.rule_button);


        userList = new ArrayList<>();
        userList.add(new User("toan", "toan123"));
        userList.add(new User("dang", "dang123"));
        userList.add(new User("phung", "phung123"));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if (isValidUser(username, password)) {
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean isValidUser(String username, String password) {
                for (User user : userList) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        return true;
                    }
                }
                return false;
            }
        });
        ruleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog popupDialog = new Dialog(Login.this);
                popupDialog.setCancelable(false);
                popupDialog.setContentView(R.layout.tutorial_play);
                Button btnContinue = (Button) popupDialog.findViewById(R.id.btnContinue);
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupDialog.cancel();
                    }
                });
                popupDialog.show();



            }
        });
    }

}




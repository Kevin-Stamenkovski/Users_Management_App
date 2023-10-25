package app.netlify.usersmanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private UserDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button registerBtn = findViewById(R.id.registerBtn);
        Button loginBtn = findViewById(R.id.loginBtn);
        Button resetBtn = findViewById(R.id.resetBtn);
        EditText loginUser = findViewById(R.id.loginUser);
        EditText loginPass = findViewById(R.id.loginPass);
        EditText registerUsername = findViewById(R.id.registerUsername);
        EditText registerEmail = findViewById(R.id.registerEmail);
        EditText registerPass = findViewById(R.id.registerPass);
        EditText confirmPass = findViewById(R.id.confirmPass);
        Switch actionSelector = findViewById(R.id.actionSelector);

        dataSource = new UserDataSource(this);
        dataSource.open();


        registerBtn.setEnabled(true);
        registerUsername.setEnabled(true);
        registerEmail.setEnabled(true);
        registerPass.setEnabled(true);
        loginBtn.setEnabled(false);
        loginUser.setEnabled(false);
        loginPass.setEnabled(false);
        actionSelector.setText("REGISTER");

        actionSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionSelector.isChecked()){
                    registerBtn.setEnabled(false);
                    registerUsername.setEnabled(false);
                    registerEmail.setEnabled(false);
                    registerPass.setEnabled(false);
                    confirmPass.setEnabled(false);
                    loginBtn.setEnabled(true);
                    loginUser.setEnabled(true);
                    loginPass.setEnabled(true);
                    actionSelector.setText("LOGIN");
                }else{
                    registerBtn.setEnabled(true);
                    registerUsername.setEnabled(true);
                    registerEmail.setEnabled(true);
                    registerPass.setEnabled(true);
                    confirmPass.setEnabled(true);
                    loginBtn.setEnabled(false);
                    loginUser.setEnabled(false);
                    loginPass.setEnabled(false);
                    actionSelector.setText("REGISTER");
                }
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerUsername.getText().length() < 3){
                    Toast.makeText(MainActivity.this, "Check The Username Length, at least 3 chars", Toast.LENGTH_SHORT).show();
                }else if(registerEmail.getText().length() < 5){
                    Toast.makeText(MainActivity.this, "Check The Email Length, at least 5 chars", Toast.LENGTH_SHORT).show();
                }else if(registerPass.getText().length() < 8){
                    Toast.makeText(MainActivity.this, "Check The Password Length, at least 8 chars", Toast.LENGTH_SHORT).show();
                }else if(!registerPass.getText().toString().matches(confirmPass.getText().toString())){
                    Toast.makeText(MainActivity.this, "Passwords Don't Match"+ registerPass.getText().toString() + confirmPass.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
//                    DATA READY TO REGISTER
                    User newUser = new User();

                    newUser.setEmail(registerEmail.getText().toString());
                    newUser.setPassword(registerPass.getText().toString());
                    newUser.setUsername(registerUsername.getText().toString());
                    dataSource.createUser(newUser);
                    Toast.makeText(MainActivity.this, "REGISTERED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "WORkS!", Toast.LENGTH_SHORT).show();
//                CHECK IF FIELDS NOT NULL AND CHEcK DATABASE
                if(loginUser.getText().length() < 3){
                    Toast.makeText(MainActivity.this, "Check The Username Length, at least 3 chars", Toast.LENGTH_SHORT).show();
                }else if(loginPass.getText().length() < 8){
                    Toast.makeText(MainActivity.this, "Check The Password Length, at least 8 chars", Toast.LENGTH_SHORT).show();
                }else{
//                    TODO: CHECK IF DATA IS REGISTERED
                    User loggedUser = dataSource.loginUser(loginUser.getText().toString(), loginPass.getText().toString());
                    if(loggedUser != null){
                        Toast.makeText(MainActivity.this, "Connected Successfully!, WELCOME "+ loggedUser.getId() + ", " +loggedUser.getUsername() +", "+ loggedUser.getEmail(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Not Connected!", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(MainActivity.this, LoggedActivity.class);



                    startActivity(intent);
                }
                setContentView(R.layout.logged_page);
            }
        });

    resetBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure You want to reset the fields? ");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loginUser.setText("");
                    loginPass.setText("");
                    registerUsername.setText("");
                    registerEmail.setText("");
                    registerPass.setText("");
                    confirmPass.setText("");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

}

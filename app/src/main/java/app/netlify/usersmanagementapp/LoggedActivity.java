package app.netlify.usersmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoggedActivity extends AppCompatActivity {
    TextView id = findViewById(R.id.userID);
    TextView username = findViewById(R.id.userName);
    TextView email = findViewById(R.id.userEmail);
    TextView password = findViewById(R.id.userPassword);

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.logged_page);


//        user = mainActivity.getLoggedUser();

        Toast.makeText(this, "Logged With " + username, Toast.LENGTH_SHORT).show();
//        this.id.setText();
//        this.username.setText();
//        this.email.setText();
//        this.password.setText();
        
    }

}

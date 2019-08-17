package blackskystudio.com.dinaskebersihan;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail     = (EditText)findViewById(R.id.input_email);
        etPassword  = (EditText)findViewById(R.id.input_password);
    }


    public void Login(View view) {
        Log.d("user", etEmail.getText().toString());
        Log.d("pass", etPassword.getText().toString());
        if (etEmail.getText().toString().equalsIgnoreCase("user@mail.com")
                && etPassword.getText().toString().equalsIgnoreCase("user")){
            Intent i = new Intent(Login.this, menu.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "E-Mail atau Password Salah!",
                    Toast.LENGTH_LONG).show();
        }
    }
}

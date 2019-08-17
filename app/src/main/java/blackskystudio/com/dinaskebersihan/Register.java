package blackskystudio.com.dinaskebersihan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        pass1 = (EditText)findViewById(R.id.etDPwd1);
        pass2 = (EditText)findViewById(R.id.etDPwd2);
    }

    public void do_register(View view) {
        if (pass1.getText().toString().equalsIgnoreCase(pass2.getText().toString())){

            Intent i = new Intent(Register.this, Login.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Isian password berbeda!",
                    Toast.LENGTH_LONG).show();
        }
    }
}

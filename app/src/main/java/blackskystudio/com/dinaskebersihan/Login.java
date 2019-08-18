package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://projectbsdinaskebersihan.000webhostapp.com/auth.php";

//        progress.show();

        //menyiapkan request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            int hasil = Integer.parseInt(jsonObject.getString("response"));

                            if (hasil == 1){
                                String ini = jsonObject.getString("id");

                                Intent i = new Intent(Login.this, menu.class);
                                startActivity(i);

//                                i.putExtra("id",ini);

                                SharedPreferences mSettings     = Login.this.getSharedPreferences("data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("id", ini);
                                editor.apply();

//                                progress.dismiss();
                                startActivity(i);
                            }else if (hasil == 0){
//                                progress.dismiss();
                                Toast.makeText(Login.this, "E-Mail atau Password Salah!",
                                        Toast.LENGTH_LONG).show();
                            }else{
//                                progress.dismiss();
                                Toast.makeText(Login.this,
                                        "Username atau password salah!",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,
                                error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",etPassword.getText().toString());
                params.put("password",etPassword.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void goto_register(View view) {
        Intent i = new Intent(Login.this, Register.class);
        startActivity(i);
    }

}

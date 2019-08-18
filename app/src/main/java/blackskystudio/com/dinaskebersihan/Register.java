package blackskystudio.com.dinaskebersihan;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    private EditText etDNama, etDAlamat, etDHP, etDEmail, etDPwd1, etDPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        etDNama     = (EditText)findViewById(R.id.etDNama);
        etDAlamat   = (EditText)findViewById(R.id.etDAlamat);
        etDHP       = (EditText)findViewById(R.id.etDHP);
        etDEmail    = (EditText)findViewById(R.id.etDEmail);
        etDPwd1     = (EditText)findViewById(R.id.etDPwd1);
        etDPwd2     = (EditText)findViewById(R.id.etDPwd2);
    }

    public void do_register(View view) {
        if (etDPwd1.getText().toString().equalsIgnoreCase(etDPwd2.getText().toString())){
            RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "http://192.168.1.5/Dinas_Kebersihan/insert.php";

//          progress.show();

            //menyiapkan request
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            try{
                                JSONObject jsonObject = new JSONObject(response);

                                String hasil = jsonObject.getString("response");

                                if (hasil.equalsIgnoreCase("success")){
                                    Intent i = new Intent(Register.this, Login.class);
                                    startActivity(i);
                                }else if (hasil.equalsIgnoreCase("failed")){
//                                progress.dismiss();
                                    Toast.makeText(Register.this, "Gagal terdaftar!",
                                            Toast.LENGTH_LONG).show();
                                }else{
//                                progress.dismiss();
                                    Toast.makeText(Register.this, "Gagal terdaftar!",
                                            Toast.LENGTH_LONG).show();
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
                            Toast.makeText(Register.this,
                                    error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Nama",etDNama.getText().toString());
                    params.put("Alamat",etDAlamat.getText().toString());
                    params.put("No_telp",etDHP.getText().toString());
                    params.put("email",etDEmail.getText().toString());
                    params.put("password",etDPwd1.getText().toString());
                    params.put("function","register");
                    return params;
                }
            };
            queue.add(postRequest);
        }else{
            Toast.makeText(this, "Isian password berbeda!",
                    Toast.LENGTH_LONG).show();
        }
    }
}

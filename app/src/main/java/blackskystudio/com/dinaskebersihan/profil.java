package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profil extends AppCompatActivity {

    private EditText etPNama, etPAlamat, etPEmail, etPNHP, etPKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        etPNama = (EditText)findViewById(R.id.etPNama);
        etPAlamat = (EditText)findViewById(R.id.etPAlamat);
        etPEmail = (EditText)findViewById(R.id.etPEmail);
        etPNHP = (EditText)findViewById(R.id.etPNHP);
        etPKB = (EditText)findViewById(R.id.etPKB);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.5/Dinas_Kebersihan/read.php";

//        progress.show();

        //menyiapkan request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray hasil = jsonObject.getJSONArray("result");

                            if (hasil != null) {
                                etPNama.setText(hasil.getJSONObject(0).getString("nama").toString());
                                etPAlamat.setText(hasil.getJSONObject(0).getString("alamat").toString());
                                etPEmail.setText(hasil.getJSONObject(0).getString("email").toString());
                                etPNHP.setText(hasil.getJSONObject(0).getString("notelp").toString());
                                etPKB.setText(hasil.getJSONObject(0).getString("kode").toString());
                            }else{
//                                progress.dismiss();
                                Toast.makeText(profil.this,
                                        "Gagal menghubungi server!",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(profil.this,
                                error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

//                Bundle bundle = getIntent().getExtras();
//                String id_user = bundle.getString("id");
                SharedPreferences mSettings = profil.this.getSharedPreferences("data", Context.MODE_PRIVATE);
                String id_user = mSettings.getString("id", "missing");

                params.put("id_user",id_user);
                params.put("function","getProfil");

                return params;
            }
        };
        queue.add(postRequest);
    }
}

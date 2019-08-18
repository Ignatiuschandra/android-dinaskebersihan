package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class layanan extends AppCompatActivity {

    private CheckBox sAlam, sRT, sKon, sKan, sInd, sOK;

    Sampah sampah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);

        sAlam   = (CheckBox)findViewById(R.id.cb_sampah1);
        sRT     = (CheckBox)findViewById(R.id.cb_sampah2);
        sKon    = (CheckBox)findViewById(R.id.cb_sampah3);
        sKan    = (CheckBox)findViewById(R.id.cb_sampah4);
        sInd    = (CheckBox)findViewById(R.id.cb_sampah5);
        sOK     = (CheckBox)findViewById(R.id.cb_sampah6);

        sampah  = new Sampah();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void send_layanan(View view) {
        RequestQueue queue = Volley.newRequestQueue(layanan.this);
        final String url = "https://projectbsdinaskebersihan.000webhostapp.com/insert.php";

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
                                AlertDialog.Builder builder = new AlertDialog.Builder(layanan.this);
                                builder.setMessage("Terima kasih telah memakai layanan kami. Sampah Anda akan segera kami ambil dalam waktu kurang lebih 30 menit");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        Intent i = new Intent(layanan.this, menu.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        Bundle bundle = getIntent().getExtras();
                                        String id_u = bundle.getString("id");

                                        i.putExtra("id",id_u);
                                        startActivity(i);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else if (hasil.equalsIgnoreCase("failed")){
                                Toast.makeText(layanan.this, "Gagal mengirim request!",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(layanan.this,
                                        "Gagal terhubung ke server!",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(layanan.this,
                                error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                if (sAlam.isChecked()){
                    sampah.sAlam = true;
                }
                if(sRT.isChecked()){
                    sampah.sRT = true;
                }
                if(sKon.isChecked()){
                    sampah.sKon = true;
                }
                if(sKan.isChecked()){
                    sampah.sKan = true;
                }
                if(sInd.isChecked()){
                    sampah.sInd = true;
                }
                if(sOK.isChecked()){
                    sampah.sOK = true;
                }
                Gson gson = new Gson();
                String jSampah = gson.toJson(sampah);

//                Bundle bundle = getIntent().getExtras();
//                String id_user = bundle.getString("id");

                SharedPreferences mSettings = layanan.this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
                String id_user = mSettings.getString("id", "missing");

                params.put("function","");
                params.put("ID_USER",id_user);
                params.put("jenis_sampah",jSampah);

                return params;
            }
        };
        queue.add(postRequest);

    }
}

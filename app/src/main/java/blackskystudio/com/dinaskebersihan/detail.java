package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class detail extends AppCompatActivity {
    TextView dNama, dAlamat, dNHP, dSampah, d_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dNama = (TextView) findViewById(R.id.dNama);
        dAlamat = (TextView) findViewById(R.id.dAlamat);
        dNHP = (TextView) findViewById(R.id.dNoTelp);
        dSampah = (TextView) findViewById(R.id.dSampah);
        d_id = (TextView) findViewById(R.id.d_id);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://projectbsdinaskebersihan.000webhostapp.com/read.php";

        //menyiapkan request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray hasil = jsonObject.getJSONArray("result");

                            if (hasil != null) {
                                for (int i = 0; i < hasil.length(); i++) {
                                    if (hasil.getJSONObject(i).getString("id_sampah").equalsIgnoreCase(getIntent().getStringExtra("id_sampah"))) {
                                        dNama.setText(hasil.getJSONObject(i).getString("nama"));
                                        dAlamat.setText(hasil.getJSONObject(i).getString("alamat"));
                                        dNHP.setText(hasil.getJSONObject(i).getString("no_hp"));
                                        d_id.setText(hasil.getJSONObject(i).getString("id_sampah"));
                                        String jsonSampah = hasil.getJSONObject(i).getString("jenis_sampah");

                                        Gson gson = new Gson();
                                        Sampah sampah = gson.fromJson(jsonSampah, Sampah.class);

                                        String sampahOk = "";
                                        if (sampah.sAlam == true) {
                                            sampahOk += "- Sampah Alam\n";
                                        }
                                        if (sampah.sRT == true) {
                                            sampahOk += "- Sampah Rumah Tangga\n";
                                        }
                                        if (sampah.sKon == true) {
                                            sampahOk += "- Sampah Konsumsi\n";
                                        }
                                        if (sampah.sKan == true) {
                                            sampahOk += "- Sampah Perkantoran\n";
                                        }
                                        if (sampah.sInd == true) {
                                            sampahOk += "- Sampah Industri\n";
                                        }
                                        if (sampah.sOK == true) {
                                            sampahOk += "- Sampah Olahan Kimia\n";
                                        }

                                        dSampah.setText(sampahOk);
                                    }
                                }
                            } else {
//                                progress.dismiss();
                                Toast.makeText(detail.this,
                                        "Gagal menghubungi server!", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detail.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "");

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void updateData(View view) {
        RequestQueue queue = Volley.newRequestQueue(detail.this);
        final String url = "https://projectbsdinaskebersihan.000webhostapp.com/update.php";

        //menyiapkan request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int hasil = jsonObject.getInt("result");

                            if (hasil == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(detail.this);
                                builder.setMessage("Pastikan sampah sudah benar-benar terambil di lokasi pengguna!");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        Intent i = new Intent(detail.this, DaftarRequest.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else if (hasil == 0) {
                                Toast.makeText(detail.this, "Gagal mengupdate status!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(detail.this,
                                        "Gagal terhubung ke server!", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detail.this,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id_sampah", d_id.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void logout2(View view) {
        Intent i = new Intent(detail.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}

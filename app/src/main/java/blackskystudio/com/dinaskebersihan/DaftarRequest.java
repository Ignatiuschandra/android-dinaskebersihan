package blackskystudio.com.dinaskebersihan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DaftarRequest extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSampah adapterSampah;
    private ArrayList<ItemSampah> itemSampahArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_request);

        addData();
    }

    void addData(){
        itemSampahArrayList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://projectbsdinaskebersihan.000webhostapp.com/read.php";

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
                                for(int i=0; i<hasil.length(); i++){
                                    itemSampahArrayList.add(
                                            new ItemSampah(
                                                    hasil.getJSONObject(i).getString("id_sampah"),
                                                    hasil.getJSONObject(i).getString("nama"),
                                                    hasil.getJSONObject(i).getString("alamat"),
                                                    hasil.getJSONObject(i).getString("jumlah")
                                            )
                                    );
                                }
                                recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                                adapterSampah = new AdapterSampah(itemSampahArrayList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DaftarRequest.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapterSampah);
                            }else{
//                                progress.dismiss();
                                Toast.makeText(DaftarRequest.this,
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
                        Toast.makeText(DaftarRequest.this,
                                error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("function","");

                return params;
            }
        };
        queue.add(postRequest);
    }
}

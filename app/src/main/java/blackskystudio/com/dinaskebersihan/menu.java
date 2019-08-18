package blackskystudio.com.dinaskebersihan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void goto_info(View view) {
        Intent i = new Intent(menu.this, Informasi.class);
        startActivity(i);
    }

    public void goto_about(View view) {
        Intent i = new Intent(menu.this, Tentang.class);
        startActivity(i);
    }

    public void goto_layanan(View view) {
        Intent i = new Intent(menu.this, layanan.class);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        i.putExtra("id",id);
        startActivity(i);
    }

    public void logout(View view) {
        Intent i = new Intent(menu.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void goto_profil(View view) {
        Intent i = new Intent(menu.this, profil.class);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        i.putExtra("id",id);

        startActivity(i);
    }
}

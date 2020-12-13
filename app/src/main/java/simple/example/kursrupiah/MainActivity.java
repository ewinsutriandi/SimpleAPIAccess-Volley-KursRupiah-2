package simple.example.kursrupiah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tglKurs;
    FloatingActionButton btnRefresh;
    ProgressBar loadingIndicator;
    JSONObject kursTerbaru;
    ListView listNilaiKurs;
    ArrayAdapter<NilaiKurs> adapterKurs;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        ambilDataKurs();
    }


    private void inisialisasiView() {
        tglKurs = findViewById(R.id.tglKurs);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> ambilDataKurs());
        listNilaiKurs = findViewById(R.id.list_nilai_kurs);
        adapterKurs = new KursAdapter(this,new ArrayList<>());;
        listNilaiKurs.setAdapter(adapterKurs);
    }

    private void ambilDataKurs() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://api.exchangeratesapi.io/latest?base=IDR";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        kursTerbaru = response;

                        refreshView();
                        loadingIndicator.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void refreshView() {
        adapterKurs.clear();
        try {
            tglKurs.setText(kursTerbaru.getString("date"));
            List<NilaiKurs> nilaiKursList = new ArrayList<>();
            JSONObject rates = kursTerbaru.getJSONObject("rates");
            for (Iterator<String> it = rates.keys(); it.hasNext(); ) {
                String mataUang = it.next();
                double nilaiTukar =  1/rates.getDouble(mataUang);
                NilaiKurs nilaiKurs = new NilaiKurs(mataUang,nilaiTukar);
                nilaiKursList.add(nilaiKurs);
            }
            adapterKurs.addAll(nilaiKursList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
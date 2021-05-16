package com.example.retrofit.models;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofit.R;
import com.example.retrofit.interfaces.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView edtPalabra;
    Button btnBuscar;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPalabra = findViewById(R.id.edtPalabra);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtResultado = findViewById(R.id.txtResultado);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResultado.setText("");
                resultado(edtPalabra.getText().toString());
            }
        });
    }

    public void resultado(String q){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.find(q);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> postList = response.body();
                for (Post p: postList){
                    String resultado="";
                    resultado+= "UserID:" + p.getUserId() + "\n";
                    resultado+= "ID:" + p.getId() + "\n";
                    resultado+= "Title:" + p.getTitle() + "\n";
                    resultado+= "Body:" + p.getBody() + "\n\n";
                    txtResultado.append(resultado);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txtResultado.setText(t.getMessage());
            }
        });
    }
}
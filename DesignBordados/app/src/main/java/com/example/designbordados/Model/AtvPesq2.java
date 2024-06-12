package com.example.designbordados.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designbordados.R;

public class AtvPesq2 extends AppCompatActivity{

    TextView edtcliente;
    TextView edtnp;
    TextView edtdatentrada;
    TextView edtdatsaida;
    TextView edtstatus;
    TextView edtinfadd;

    Button btnvoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.atv_pesq2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtcliente = findViewById(R.id.edtcliente);
        edtnp = findViewById(R.id.edtnp);
        edtdatentrada = findViewById(R.id.edtdatentrada);
        edtdatsaida = findViewById(R.id.edtdatsaida);
        edtstatus = findViewById(R.id.edtstatus);
        edtinfadd = findViewById(R.id.edtinfadd);
        btnvoltar = findViewById(R.id.btnvoltar);

        Ordem ordem = (Ordem) getIntent().getSerializableExtra("ordem");

        if (ordem != null) {
            edtcliente.setText(ordem.getCliente().getNome());
            edtnp.setText(ordem.getNumero());
            edtdatentrada.setText(ordem.getDatEntrada());
            edtdatsaida.setText(ordem.getDatSaida());
            edtstatus.setText(ordem.getStatus());
            edtinfadd.setText(ordem.getInfAdd());
        }

        btnvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}




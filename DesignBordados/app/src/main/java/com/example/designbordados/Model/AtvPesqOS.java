package com.example.designbordados.Model;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designbordados.Dao.OrdemDao;
import com.example.designbordados.R;

public class AtvPesqOS extends AppCompatActivity {

    EditText edtpesq;
    Button btnPesq;
    OrdemDao ordemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.atv_pesq_os);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtpesq = findViewById(R.id.edtpesq);
        btnPesq = findViewById(R.id.btnPesq);

        ordemDao = new OrdemDao(this);

        btnPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroOS = edtpesq.getText().toString().trim();

                if (!numeroOS.isEmpty()) {
                    Ordem ordem = ordemDao.buscarPorNumero(numeroOS);
                    if (ordem != null) {
                        Intent intent = new Intent(AtvPesqOS.this, AtvPesq2.class);
                        intent.putExtra("ordem", ordem); // Passa a ordem como um extra
                        startActivity(intent);
                    } else {
                        Toast.makeText(AtvPesqOS.this, "Ordem de Serviço não encontrada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AtvPesqOS.this, "Por favor, informe o número da OS", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


}
package com.example.designbordados.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designbordados.Dao.OrdemDao;
import com.example.designbordados.R;

import java.util.ArrayList;
import java.util.List;

public class AtvOS extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    EditText editTextOSId;
    Button btnAdd;

    ListView lstOS;

    List<Ordem> listaOS = new ArrayList<Ordem>();
    ListAdapter listAdapter;
    int indice;
    OrdemDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.atv_os);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        lstOS = findViewById(R.id.lstOS);
        lstOS.setOnItemClickListener(this);

        editTextOSId = findViewById(R.id.edtTextpesq);

        dao = new OrdemDao(this);
        atualizarLista();

        // Configurações de Pesquisa - OS
        editTextOSId.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String numero = s.toString().toLowerCase();
                List<Ordem> ordensFiltradas = new ArrayList<>();

                for (Ordem ordem : listaOS) {
                    if (ordem.getNumero().toLowerCase().contains(numero)) {
                        ordensFiltradas.add(ordem);
                    }
                }

                ArrayAdapter<Ordem> adapter = new ArrayAdapter<>(AtvOS.this, android.R.layout.simple_list_item_1, ordensFiltradas);
                lstOS.setAdapter(adapter);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        listaOS = dao.listar(editTextOSId.getText().toString());
        listAdapter = new ArrayAdapter<Ordem>(this, android.R.layout.simple_list_item_1, listaOS);
        lstOS.setAdapter(listAdapter);

    }

    @Override
    public void onClick(View v) {

        if (v == btnAdd) {
            Ordem c = new Ordem();
            c.setId(0L);
            abrirCadastro("INSERIR", c);
        } else {
            finish();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        indice = position;
        Ordem c = (Ordem) lstOS.getAdapter().getItem(position);
        abrirCadastro("ALTERAR", c);
    }

    private void abrirCadastro(String acao, Ordem obj) {
        Intent telaCadOS = new Intent(this, AtvCadOS.class);
        Bundle extras = new Bundle();
        extras.putString("acao", acao);
        extras.putSerializable("obj", obj);
        telaCadOS.putExtras(extras);
        startActivity(telaCadOS);
    }

}
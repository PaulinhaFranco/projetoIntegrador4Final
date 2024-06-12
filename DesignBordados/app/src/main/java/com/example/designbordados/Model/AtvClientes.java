package com.example.designbordados.Model;

import static android.widget.AdapterView.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import com.example.designbordados.Dao.ClienteDao;
import com.example.designbordados.R;

import java.util.ArrayList;
import java.util.List;

public class AtvClientes extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    Button btnAdd;
    EditText editTextClientId;
    ListView lstCliente;
    List<Cliente> listaClientes = new ArrayList<Cliente>();
    ListAdapter listAdapter;
    int indice;
    ClienteDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.atv_clientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        lstCliente = findViewById(R.id.lstCliente);
        lstCliente.setOnItemClickListener(this);

        editTextClientId = findViewById(R.id.editTextClientId);


        dao = new ClienteDao(this);
        atualizarLista();

        editTextClientId.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String nomeCliente = s.toString().toLowerCase();
                List<Cliente> clientesFiltrados = new ArrayList<>();

                for (Cliente cliente : listaClientes) {
                    if (cliente.getNome().toLowerCase().contains(nomeCliente)) {
                        clientesFiltrados.add(cliente);
                    }
                }

                ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(AtvClientes.this, android.R.layout.simple_list_item_1, clientesFiltrados);
                lstCliente.setAdapter(adapter);
            }
        });
    }



    @Override
    protected void onResume(){
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        listaClientes = dao.listar();
        listAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, listaClientes);
        lstCliente.setAdapter(listAdapter);

    }

    @Override
    public void onClick(View v) {

        if (v == btnAdd) {
            Cliente c = new Cliente();
            c.setId(0L);
            abrirCadastro("INSERIR", c);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        indice = position;
        Cliente c = (Cliente) lstCliente.getAdapter().getItem(position);
        abrirCadastro("ALTERAR", c);
    }

    private void abrirCadastro(String acao, Cliente obj) {
        Intent telaCad = new Intent(this, AtvCadCliente.class);
        Bundle extras = new Bundle();
        extras.putString("acao",acao);
        extras.putSerializable("obj",obj);
        telaCad.putExtras(extras);
        startActivity(telaCad);
    }



}
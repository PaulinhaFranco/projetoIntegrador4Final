package com.example.designbordados.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designbordados.Dao.ClienteDao;
import com.example.designbordados.Dao.OrdemDao;
import com.example.designbordados.R;

import java.util.List;

public class AtvCadOS extends AppCompatActivity implements View.OnClickListener {


    EditText edtCodOS;
    EditText edtnumOS;
    EditText edtDataEnt;
    EditText edtDatasaida;
    EditText edtinfAdd;
    Spinner spstatus;
    Spinner spclienteOS;

    Button btnExcluir;
    Button btnVoltar;
    Button btnAdd;
    String acao;
    Ordem c;
    OrdemDao dao;
    ClienteDao clienteDao;
    List<Cliente> listaClient;
    BaseAdapter baseAdapter;

    private void criarComponentes() {
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnAdd.setText(acao);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        btnExcluir = findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(this);

        if (acao.equals("INSERIR"))
            btnExcluir.setVisibility(View.INVISIBLE);
        else
            btnExcluir.setVisibility(View.VISIBLE);

        edtCodOS = findViewById(R.id.edtCodOS);
        edtnumOS = findViewById(R.id.edtnumOS);
        edtDataEnt = findViewById(R.id.edtDataEnt);
        edtDatasaida = findViewById(R.id.edtDatasaida);
        spclienteOS = findViewById(R.id.spclienteOS);
        spstatus = findViewById(R.id.spstatus);
        edtinfAdd = findViewById(R.id.edtinfAdd);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_options, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spstatus.setAdapter(statusAdapter);

        clienteDao = new ClienteDao(this);
        listaClient = clienteDao.listar();
        baseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaClient);
        spclienteOS.setAdapter(baseAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.atv_cad_os);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        acao = getIntent().getExtras().getString("acao");
        dao = new OrdemDao(this);
        criarComponentes();

        if (getIntent().getExtras().getSerializable("obj") != null) {
            c = (Ordem) getIntent().getExtras().getSerializable("obj");

            edtCodOS.setText(c.getId().toString());
            edtnumOS.setText(c.getNumero());
            edtDataEnt.setText(c.getDatEntrada());
            edtDatasaida.setText(c.getDatSaida());
            edtinfAdd.setText(c.getInfAdd());

            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spstatus.getAdapter();
            int statusPosition = adapter.getPosition(c.getStatus());
            spstatus.setSelection(statusPosition);

            int ind = retornarIndiceCliente(c.getCliente().getId());
            spclienteOS.setSelection(ind);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnVoltar) {
            finish();

        } else if (v == btnExcluir) {
            long id = dao.EXCLUIR(c);
            Toast.makeText(this, "Ordem " + c.getNumero() + " excluida com sucesso!", Toast.LENGTH_LONG).show();
            finish();

        } else if (v == btnAdd) {

            c.setCliente((Cliente) spclienteOS.getSelectedItem());
            c.setNumero(edtnumOS.getText().toString());
            c.setDatEntrada(edtDataEnt.getText().toString());
            c.setDatSaida(edtDatasaida.getText().toString());
            c.setStatus(spstatus.getSelectedItem().toString());
            c.setInfAdd(edtinfAdd.getText().toString());


            if (acao.equals("INSERIR")) {
                long id = dao.INSERIR(c);
                Toast.makeText(this, "Ordem " + c.getNumero() + "inserida com id = " + id, Toast.LENGTH_LONG).show();
            } else {
                long id = dao.ALTERAR(c);
                Toast.makeText(this, "Ordem " + c.getNumero() + "alterada com sucesso!", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


    int retornarIndiceCliente(Long idCliente) {
        int indice = 0;
        for (Cliente obj : listaClient) {
            if (obj.getId().equals(idCliente)) {
                return indice;
            }
            indice++;
        }
        return 0;
    }
}

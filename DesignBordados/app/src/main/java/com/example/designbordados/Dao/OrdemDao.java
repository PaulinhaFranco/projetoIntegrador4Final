package com.example.designbordados.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.designbordados.Model.Cliente;
import com.example.designbordados.Model.Ordem;

import java.util.ArrayList;
import java.util.List;


public class OrdemDao {

    private final String TABELA = "ordem";
    private final Conexao conexao;
    private final SQLiteDatabase banco;


    public OrdemDao(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    private ContentValues preencherValores(Ordem ordem) {
        ContentValues values = new ContentValues();

        values.put("numero", ordem.getNumero());
        values.put("dat_entrada", ordem.getDatEntrada());
        values.put("dat_saida", ordem.getDatSaida());
        values.put("status", ordem.getStatus());
        values.put("inf_add", ordem.getInfAdd());
        values.put("id_cliente", ordem.getCliente().getId());

        return values;
    }

    public long INSERIR(Ordem ordem) {
        ContentValues values = preencherValores(ordem);
        return banco.insert(TABELA, null, values);
    }

    public long ALTERAR(Ordem ordem) {
        ContentValues values = preencherValores(ordem);
        return banco.update(TABELA, values, "id = ?", new String[]{ordem.getId().toString()});

    }

    public long EXCLUIR(Ordem ordem) {
        return banco.delete(TABELA, "id = ?", new String[]{ordem.getId().toString()});
    }


    public List<Ordem> listar(String numOS) {

        String SQL = "select o.id, o.numero, o.dat_entrada, o.dat_saida, o.status, o.inf_add, " +
                " o.id_cliente, c.nome from ordem o inner join cliente c on o.id_cliente = c.id " +
                " where o.numero like ? order by o.id";

        Cursor c = banco.rawQuery(SQL, new String[]{"%" + numOS + "%"});

        List<Ordem> lista = new ArrayList<>();

        while (c.moveToNext()) {
            Ordem ordem = new Ordem();
            ordem.setId(c.getLong(0));
            ordem.setNumero(c.getString(1));
            ordem.setDatEntrada(c.getString(2));
            ordem.setDatSaida(c.getString(3));
            ordem.setStatus(c.getString(4));
            ordem.setInfAdd(c.getString(5));
            ordem.getCliente().setId(c.getLong(6));
            ordem.getCliente().setNome(c.getString(7));
            lista.add(ordem);
        }
        c.close();
        return lista;
    }

    public Ordem buscarPorNumero(String numero) {
        String SQL = "select o.id, o.numero, o.dat_entrada, o.dat_saida, o.status, o.inf_add, " +
                " o.id_cliente, c.nome from ordem o inner join cliente c on o.id_cliente = c.id " +
                " where o.numero = ?";

        Cursor c = banco.rawQuery(SQL, new String[]{numero});

        if (c.moveToFirst()) {
            Ordem ordem = new Ordem();
            ordem.setId(c.getLong(0));
            ordem.setNumero(c.getString(1));
            ordem.setDatEntrada(c.getString(2));
            ordem.setDatSaida(c.getString(3));
            ordem.setStatus(c.getString(4));
            ordem.setInfAdd(c.getString(5));
            Cliente cliente = new Cliente(); // Criar um novo Cliente
            cliente.setId(c.getLong(6));
            cliente.setNome(c.getString(7));
            ordem.setCliente(cliente); // Definir o cliente na ordem
            c.close();
            return ordem;
        }
        c.close();
        return null;
    }

}



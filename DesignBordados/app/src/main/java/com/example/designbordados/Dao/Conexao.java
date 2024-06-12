package com.example.designbordados.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {


    private static final String NAME = "banco.db";
    private static final int VERSION = 2;

    private static final String SQL_CREATE_CLIENTE = "create table cliente ( " +
            " id integer primary key autoincrement, " +
            " nome varchar(50), " +
            " fone varchar(20), " +
            " cpf varchar(50), " +
            " email varchar(50));";

    private static final String SQL_CREATE_ORDEM = "create table ordem ( " +
            " id integer primary key autoincrement, " +
            " id_cliente integer not null, " +
            " numero varchar(50), " +
            " dat_entrada varchar(50), " +
            " dat_saida varchar(50), " +
            " status varchar(50), " +
            " inf_add varchar(50), " +
            "constraint rel_cliente_ordem foreign key(id_cliente) references cliente(id));";


    public Conexao(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CLIENTE);
        db.execSQL(SQL_CREATE_ORDEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ordem");
        db.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(db);

    }

}

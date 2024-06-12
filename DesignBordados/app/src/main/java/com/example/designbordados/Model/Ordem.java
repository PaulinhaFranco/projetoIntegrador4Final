package com.example.designbordados.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ordem implements Serializable {

    private Long id;

    private String numero;

    private String datEntrada;
    private String datSaida;

    private String status;
    private String infAdd;
    private Cliente cliente;

    public Ordem() {
        this.cliente = new Cliente();
    }

    @NonNull
    @Override
    public String toString() {
        return this.numero + " - " + this.getCliente().getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDatEntrada() {
        return datEntrada;
    }

    public void setDatEntrada(String datEntrada) {
        this.datEntrada = datEntrada;
    }

    public String getDatSaida() {
        return datSaida;
    }

    public void setDatSaida(String datSaida) {
        this.datSaida = datSaida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfAdd() {
        return infAdd;
    }

    public void setInfAdd(String infAdd) {
        this.infAdd = infAdd;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

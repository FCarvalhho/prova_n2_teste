/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.exception;

import java.time.LocalDateTime;

/**
 *
 * @author Kayqu
 */
public class ErroPadrao {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String caminho;

    public ErroPadrao() {
    }

    public ErroPadrao(LocalDateTime timestamp, Integer status, String erro, String caminho) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.caminho = caminho;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}

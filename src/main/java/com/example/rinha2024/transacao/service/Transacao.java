package com.example.rinha2024.transacao.service;

import com.example.rinha2024.shared.TipoTransacao;


public record Transacao(
    int valor,
    TipoTransacao tipoTransacao,
    String descricao
) {

    public boolean isValid() {
        return descricao.length() < 10 && descricao.length() > 1 && valor > 0;
    }

}

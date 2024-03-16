package com.example.rinha2024.extrato.service;

import com.example.rinha2024.shared.TipoTransacao;

import java.time.LocalDateTime;
import java.util.Collection;

public record Extrato(
    Saldo saldo,
    Collection<Transacao> ultimasTransacoes
) {

    public record Saldo(
            int total,
            int limite,
            LocalDateTime dataExtrato
    ) {}

    public record Transacao(
            int valor,
            TipoTransacao tipo,
            String descricao,
            LocalDateTime realizadaEm
    ) {
    }

}

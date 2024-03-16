package com.example.rinha2024.transacao.service;

import com.example.rinha2024.shared.TipoTransacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor @Slf4j
public class TransacaoService {
    private final JdbcClient jdbcClient;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransacaoEfetuada transacaoPara(int clientId, Transacao transacao) {

        var row = jdbcClient
            .sql("""
                select limite, valor from carteira where id = ? for update
            """)
            .params(clientId).query().singleRow();

        if (row.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente nao encontrado!");
        }

        int limite = (int) row.get("limite");
        int saldo = (int) row.get("valor");

        int diff = transacao.valor();
        if (transacao.tipoTransacao().equals(TipoTransacao.d)) {
            diff = diff * -1;
        }

        int novoSaldo = saldo + diff;
        if (novoSaldo < (limite * -1)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "sem limite!");
        }

        jdbcClient.sql("""
            update carteira set valor = ? where id = ?
        """)
        .params(novoSaldo, clientId).update();

        jdbcClient.sql("""
            insert into transacoes(valor, tipo, descricao, id_clientes)
             values(?, (?)::tipo_transacao, ?, ?)
        """)
        .params(transacao.valor(), transacao.tipoTransacao().name(), transacao.descricao(), clientId).update();

        return new TransacaoEfetuada(limite, novoSaldo);
    }

}

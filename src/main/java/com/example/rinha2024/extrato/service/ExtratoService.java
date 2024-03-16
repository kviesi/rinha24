package com.example.rinha2024.extrato.service;

import com.example.rinha2024.shared.TipoTransacao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtratoService {
    private final JdbcClient jdbcClient;

    private static final RowMapper<Extrato> EXTRATO_RM = new ExtratoRowMapper();

    @Transactional(readOnly = true)
    public Extrato geraPor(int clientId) {
        return jdbcClient.sql("""
            select
                ca.valor, ca.limite, t.valor, t.tipo, t.descricao, t.realizada_em
            from
                carteira ca
                left join transacoes t on t.id_clientes = ca.id
            where
                ca.id = ?
            order by
                t.realizada_em desc
            limit 10;
        """).params(clientId).query(EXTRATO_RM).optional()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
    }

    private static class ExtratoRowMapper implements RowMapper<Extrato> {

        @Override
        public Extrato mapRow(ResultSet rs, int rowNum) throws SQLException {

            Extrato.Saldo saldo = new Extrato.Saldo(
                rs.getInt(1),
                rs.getInt(2),
                LocalDateTime.now()
            );

            var transacoes = new ArrayList<Extrato.Transacao>(10);
            while (rs.next()) {
                transacoes.add(new Extrato.Transacao(
                    rs.getInt(3),
                    TipoTransacao.valueOf(rs.getString(4)),
                    rs.getString(5),
                    rs.getTimestamp(6).toLocalDateTime()
                ));
            }

            return new Extrato(saldo, transacoes);
        }
    }

}

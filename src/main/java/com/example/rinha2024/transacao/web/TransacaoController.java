package com.example.rinha2024.transacao.web;

import com.example.rinha2024.transacao.service.TransacaoEfetuada;
import com.example.rinha2024.transacao.service.TransacaoService;
import com.example.rinha2024.transacao.service.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    @PostMapping("/clientes/{id}/transacoes")
    public TransacaoEfetuada criaTransacao(
        @PathVariable int id,
        @RequestBody Transacao transacao
    ) {
        Assert.isTrue(id > 0, "");
        Assert.isTrue(transacao.isValid(), "");
        return transacaoService.transacaoPara(id, transacao);
    }

}

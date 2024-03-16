package com.example.rinha2024.extrato.web;

import com.example.rinha2024.extrato.service.Extrato;
import com.example.rinha2024.extrato.service.ExtratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ExtratoController {
    private final ExtratoService extratoService;

    @GetMapping("/clientes/{id}/extrato")
    public Extrato getByClientId(@PathVariable("id") int clientId) {
        Assert.isTrue(clientId > 0, "");
        return extratoService.geraPor(clientId);
    }

}

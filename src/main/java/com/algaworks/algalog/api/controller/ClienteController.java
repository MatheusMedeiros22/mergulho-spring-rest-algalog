package com.algaworks.algalog.api.controller;


import com.algaworks.algalog.domain.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping
    public List<Cliente> listar(){

        Cliente cliente1 = new Cliente();
        cliente1.setId(85457L);
        cliente1.setNome("Jose");
        cliente1.setEmail("jose@email.com");
        cliente1.setTelefone("(81) 8878-9857");

        Cliente cliente2 = new Cliente();
        cliente2.setId(85488L);
        cliente2.setNome("Joao");
        cliente2.setEmail("joao@email.com");
        cliente2.setTelefone("(87) 8878-7965");

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente1);

        return clientes;
    }
}

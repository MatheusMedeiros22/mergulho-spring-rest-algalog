package com.algaworks.algalog.api.controller;


import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import com.algaworks.algalog.domain.service.CatalogoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> listar(){
        return  clienteRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();

        /*
            return clienteRepository.findById(id)
                    .map(cliente -> ResponseEntity.ok(cliente))
                    //.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
         */
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
      if(!clienteRepository.existsById(id)){
          return ResponseEntity.notFound().build();
      }

      cliente.setId(id);
      cliente = catalogoClienteService.salvar(cliente);

      return ResponseEntity.ok(cliente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.api.assembler.OcorrenciaAssembler;
import com.algaworks.algalog.api.model.OcorrenciaModel;
import com.algaworks.algalog.api.model.input.OcorrenciaInput;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.service.BuscaEntregaService;
import com.algaworks.algalog.domain.service.RegistroOcorrenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{id}/ocorrencias")
public class OcorrenciaController {

    private BuscaEntregaService buscaEntregaService;
    private OcorrenciaAssembler ocorrenciaAssembler;
    private RegistroOcorrenciaService registroOcorrenciaService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel registrar(@PathVariable Long id,
                                     @Valid
                                     @RequestBody
                                     OcorrenciaInput ocorrenciaInput){
     Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
             .registrar(id, ocorrenciaInput.getDescricao());

     return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
    }

    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable Long id){
        Entrega entrega = buscaEntregaService.buscar(id);

        return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
    }
}

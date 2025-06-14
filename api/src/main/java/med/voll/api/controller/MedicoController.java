package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosDetalhamentoMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.medico.*;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping //cadastrar
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){

        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping //listar
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}") //parametro dinamico, pode ser vários valores
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //recuperando médico do banco
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}") //parametro dinamico, pode ser vários valores
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //recuperando médico do banco

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}

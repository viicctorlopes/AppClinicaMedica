package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.pacientes.DadosListagemPaciente;
import med.voll.api.domain.pacientes.Pacientes;
import med.voll.api.domain.pacientes.PacientesRepository;
import med.voll.api.domain.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacientesRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPacientes dados, UriComponentsBuilder uriBuilder) {
        var paciente = (new Pacientes(dados));
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginaçao) {
        var page = repository.findAll(paginaçao).map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaçaoPacientes atualizaçao) {
        var paciente = repository.getReferenceById(atualizaçao.id());
        paciente.atualizarInformaçoes(atualizaçao);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirPaciente(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}



package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacientesRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPacientes dados) {
        repository.save(new Pacientes(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginaçao) {
        return repository.findAll(paginaçao).map(DadosListagemPaciente::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizaçaoPacientes atualizaçao) {
        var paciente = repository.getReferenceById(atualizaçao.id());
        paciente.atualizarInformaçoes(atualizaçao);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void excluirPaciente(@PathVariable Long id){
        repository.deleteById(id);
}
}



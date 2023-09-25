package med.voll.api.domain.pacientes;

import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(String nome , String email, String cpf , String telefone , Endereco endereco) {

    public DadosDetalhamentoPaciente(Pacientes paciente){
        this(paciente.getNome(),paciente.getEmail(),paciente.getCpf(),paciente.getTelefone(),paciente.getEndereco());
    }
}

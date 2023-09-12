package med.voll.api.pacientes;

public record DadosListagemPaciente(String nome, String email, String cpf) {
    public DadosListagemPaciente(Pacientes paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
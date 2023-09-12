package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizaçaoMedicos(
        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereço) {
}

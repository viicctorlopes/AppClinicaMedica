package med.voll.api.pacientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Especialidade;


@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Pacientes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;


    @Embedded
    private Endereco endereco;

    public Pacientes(DadosCadastroPacientes dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformaçoes(DadosAtualizaçaoPacientes atualizaçao) {
        if (atualizaçao.nome() != null){
            this.nome = atualizaçao.nome();
        }
        if(atualizaçao.telefone() != null) {
            this.telefone= atualizaçao.telefone();
        }
        if (atualizaçao.endereco() != null) {
            this.endereco.atualizarInformaçoes(atualizaçao.endereco());
        }
    }
}


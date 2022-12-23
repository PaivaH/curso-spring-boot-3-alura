package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.EnderecoDTO;

public record AtualizarMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoDTO endereco) {

}

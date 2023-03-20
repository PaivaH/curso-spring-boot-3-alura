package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.EnderecoDto;

public record MedicoDtoRequest(
        @NotBlank
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull @Valid
        String telefone,
        @NotNull
        Especialidade especialidade,
        @NotNull @Valid
        EnderecoDto endereco) {
}

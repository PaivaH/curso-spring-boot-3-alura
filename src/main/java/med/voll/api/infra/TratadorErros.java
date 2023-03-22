package med.voll.api.infra;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        List<FieldError> erros = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(ErrosDto::new).toList());
    }

    private record ErrosDto(
            String campo,
            String mensagem) {

        public ErrosDto(FieldError erro) {
            this(erro.getField(), 
            erro.getDefaultMessage());
        }
    }

}

package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.MedicoDtoResponse;
import med.voll.api.medico.AtualizarMedicoDtoRequest;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoDtoRequest;
import med.voll.api.medico.MedicoRepository;


@RequestMapping("medicos")
@RestController
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoDtoRequest medicoDTO, UriComponentsBuilder componentsBuilder) {
        Medico medico = new Medico(medicoDTO);
        medicoRepository.save(medico);

        URI uri = componentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoDtoResponse(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable page) {
        Page<DadosListagemMedico> temp = medicoRepository.findAllByAtivoTrue(page).map(DadosListagemMedico::new);

        return ResponseEntity.ok(temp);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity obter(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new MedicoDtoResponse(medico));
    }

    @PutMapping()
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid AtualizarMedicoDtoRequest atualizarMedicoDTO) {
        Medico medico = medicoRepository.getReferenceById(atualizarMedicoDTO.id());
        medico.atualizarInformacoes(atualizarMedicoDTO);

        return ResponseEntity.ok(new MedicoDtoResponse(medico));
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }
}

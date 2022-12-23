package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.AtualizarMedicoDTO;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoDTO;
import med.voll.api.medico.MedicoRepository;


@RequestMapping("medicos")
@RestController
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoDTO medicoDTO) {
        medicoRepository.save(new Medico(medicoDTO));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(Pageable page) {
        return medicoRepository.findAllByAtivoTrue(page).map(DadosListagemMedico::new);
    }

    @GetMapping(path = "/{id}")
    public Medico obter(Long id) {
        return medicoRepository.findById(id).get();
    }

    @PutMapping()
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizarMedicoDTO atualizarMedicoDTO) {
        Medico medico = medicoRepository.getReferenceById(atualizarMedicoDTO.id());
        medico.atualizarInformacoes(atualizarMedicoDTO);
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}

package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.lead.ExperimentalClassLeadRequestDTO;
import com.alexandre.Judo_Candoi_Api.dto.lead.ExperimentalClassLeadResponseDTO;
import com.alexandre.Judo_Candoi_Api.model.ExperimentalClassLead;
import com.alexandre.Judo_Candoi_Api.repository.ExperimentalClassLeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperimentalClassLeadService {

    private static final String DEFAULT_OBJECTIVE = "Aula experimental";

    private final ExperimentalClassLeadRepository repository;

    public ExperimentalClassLeadService(ExperimentalClassLeadRepository repository) {
        this.repository = repository;
    }

    public ExperimentalClassLeadResponseDTO create(ExperimentalClassLeadRequestDTO dto) {
        ExperimentalClassLead draft = new ExperimentalClassLead(
                dto.name().trim(),
                dto.age(),
                dto.phone().trim(),
                normalizeObjective(dto.objective())
        );

        ExperimentalClassLead persisted = repository.save(draft);

        return new ExperimentalClassLeadResponseDTO(
                persisted.getId(),
                persisted.getName(),
                persisted.getAge(),
                persisted.getPhone(),
                persisted.getObjective(),
                persisted.getCreatedAt(),
                "Solicitacao recebida com sucesso. Entraremos em contato pelo WhatsApp."
        );
    }

    public List<ExperimentalClassLeadResponseDTO> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    private ExperimentalClassLeadResponseDTO toResponse(ExperimentalClassLead lead) {
        return new ExperimentalClassLeadResponseDTO(
                lead.getId(),
                lead.getName(),
                lead.getAge(),
                lead.getPhone(),
                lead.getObjective(),
                lead.getCreatedAt(),
                "Solicitacao armazenada"
        );
    }

    private String normalizeObjective(String objective) {
        if (objective == null || objective.isBlank()) {
            return DEFAULT_OBJECTIVE;
        }

        return objective.trim();
    }
}

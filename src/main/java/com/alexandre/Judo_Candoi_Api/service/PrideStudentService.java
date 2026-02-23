package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.pride.PrideStudentAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.pride.PrideStudentUpsertDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.PrideStudentDTO;
import com.alexandre.Judo_Candoi_Api.infra.exceptions.ResourceNotFoundException;
import com.alexandre.Judo_Candoi_Api.model.PrideStudent;
import com.alexandre.Judo_Candoi_Api.repository.PrideStudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrideStudentService {

    private final PrideStudentRepository repository;

    public PrideStudentService(PrideStudentRepository repository) {
        this.repository = repository;
    }

    public List<PrideStudentDTO> findPublic() {
        return repository.findAllByActiveTrueOrderByDisplayOrderAscIdAsc().stream()
                .map(this::toPublicDto)
                .toList();
    }

    public List<PrideStudentAdminResponseDTO> findAll() {
        return repository.findAll(Sort.by(Sort.Order.asc("displayOrder"), Sort.Order.asc("id"))).stream()
                .map(this::toAdminDto)
                .toList();
    }

    public PrideStudentAdminResponseDTO create(PrideStudentUpsertDTO dto) {
        PrideStudent student = new PrideStudent(
                dto.name().trim(),
                dto.achievement().trim(),
                dto.month().trim(),
                normalizeOptionalText(dto.imageUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(student));
    }

    public PrideStudentAdminResponseDTO update(Long id, PrideStudentUpsertDTO dto) {
        PrideStudent student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno destaque nao encontrado para id: " + id));

        student.update(
                dto.name().trim(),
                dto.achievement().trim(),
                dto.month().trim(),
                normalizeOptionalText(dto.imageUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(student));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno destaque nao encontrado para id: " + id);
        }

        repository.deleteById(id);
    }

    private boolean resolveActive(Boolean active) {
        return active == null || active;
    }

    private int resolveDisplayOrder(Integer displayOrder) {
        return displayOrder == null ? 0 : Math.max(displayOrder, 0);
    }

    private String normalizeOptionalText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    private PrideStudentDTO toPublicDto(PrideStudent student) {
        return new PrideStudentDTO(
                student.getName(),
                student.getAchievement(),
                student.getMonth(),
                student.getImageUrl()
        );
    }

    private PrideStudentAdminResponseDTO toAdminDto(PrideStudent student) {
        return new PrideStudentAdminResponseDTO(
                student.getId(),
                student.getName(),
                student.getAchievement(),
                student.getMonth(),
                student.getImageUrl(),
                student.isActive(),
                student.getDisplayOrder()
        );
    }
}

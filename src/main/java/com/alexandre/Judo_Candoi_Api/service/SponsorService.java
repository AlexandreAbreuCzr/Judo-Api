package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.site.SponsorDTO;
import com.alexandre.Judo_Candoi_Api.dto.sponsor.SponsorAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.sponsor.SponsorUpsertDTO;
import com.alexandre.Judo_Candoi_Api.infra.exceptions.ResourceNotFoundException;
import com.alexandre.Judo_Candoi_Api.model.Sponsor;
import com.alexandre.Judo_Candoi_Api.repository.SponsorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorService {

    private final SponsorRepository repository;

    public SponsorService(SponsorRepository repository) {
        this.repository = repository;
    }

    public List<SponsorDTO> findPublic() {
        return repository.findAllByActiveTrueOrderByDisplayOrderAscIdAsc().stream()
                .map(this::toPublicDto)
                .toList();
    }

    public List<SponsorAdminResponseDTO> findAll() {
        return repository.findAll(Sort.by(Sort.Order.asc("displayOrder"), Sort.Order.asc("id"))).stream()
                .map(this::toAdminDto)
                .toList();
    }

    public SponsorAdminResponseDTO create(SponsorUpsertDTO dto) {
        Sponsor sponsor = new Sponsor(
                dto.name().trim(),
                dto.description().trim(),
                dto.logoUrl().trim(),
                normalizeWebsite(dto.websiteUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(sponsor));
    }

    public SponsorAdminResponseDTO update(Long id, SponsorUpsertDTO dto) {
        Sponsor sponsor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrocinador nao encontrado para id: " + id));

        sponsor.update(
                dto.name().trim(),
                dto.description().trim(),
                dto.logoUrl().trim(),
                normalizeWebsite(dto.websiteUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(sponsor));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Patrocinador nao encontrado para id: " + id);
        }

        repository.deleteById(id);
    }

    private boolean resolveActive(Boolean active) {
        return active == null || active;
    }

    private int resolveDisplayOrder(Integer displayOrder) {
        return displayOrder == null ? 0 : Math.max(displayOrder, 0);
    }

    private String normalizeWebsite(String websiteUrl) {
        if (websiteUrl == null || websiteUrl.isBlank()) {
            return "";
        }
        return websiteUrl.trim();
    }

    private SponsorDTO toPublicDto(Sponsor sponsor) {
        return new SponsorDTO(
                sponsor.getName(),
                sponsor.getDescription(),
                sponsor.getLogoUrl(),
                sponsor.getWebsiteUrl()
        );
    }

    private SponsorAdminResponseDTO toAdminDto(Sponsor sponsor) {
        return new SponsorAdminResponseDTO(
                sponsor.getId(),
                sponsor.getName(),
                sponsor.getDescription(),
                sponsor.getLogoUrl(),
                sponsor.getWebsiteUrl(),
                sponsor.isActive(),
                sponsor.getDisplayOrder()
        );
    }
}

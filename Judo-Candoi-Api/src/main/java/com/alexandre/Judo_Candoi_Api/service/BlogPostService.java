package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.blog.BlogPostAdminResponseDTO;
import com.alexandre.Judo_Candoi_Api.dto.blog.BlogPostUpsertDTO;
import com.alexandre.Judo_Candoi_Api.dto.site.BlogPostDTO;
import com.alexandre.Judo_Candoi_Api.infra.exceptions.ResourceNotFoundException;
import com.alexandre.Judo_Candoi_Api.model.BlogPost;
import com.alexandre.Judo_Candoi_Api.repository.BlogPostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository repository;

    public BlogPostService(BlogPostRepository repository) {
        this.repository = repository;
    }

    public List<BlogPostDTO> findPublic() {
        return repository.findAllByActiveTrueOrderByDisplayOrderAscIdAsc().stream()
                .map(this::toPublicDto)
                .toList();
    }

    public List<BlogPostAdminResponseDTO> findAll() {
        return repository.findAll(Sort.by(Sort.Order.asc("displayOrder"), Sort.Order.asc("id"))).stream()
                .map(this::toAdminDto)
                .toList();
    }

    public BlogPostAdminResponseDTO create(BlogPostUpsertDTO dto) {
        String excerpt = dto.excerpt().trim();
        BlogPost post = new BlogPost(
                dto.title().trim(),
                dto.slug().trim(),
                excerpt,
                normalizeContent(dto.content(), excerpt),
                normalizeImageUrl(dto.imageUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(post));
    }

    public BlogPostAdminResponseDTO update(Long id, BlogPostUpsertDTO dto) {
        BlogPost post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post do blog nao encontrado para id: " + id));

        String excerpt = dto.excerpt().trim();
        post.update(
                dto.title().trim(),
                dto.slug().trim(),
                excerpt,
                normalizeContent(dto.content(), excerpt),
                normalizeImageUrl(dto.imageUrl()),
                resolveActive(dto.active()),
                resolveDisplayOrder(dto.displayOrder())
        );

        return toAdminDto(repository.save(post));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Post do blog nao encontrado para id: " + id);
        }

        repository.deleteById(id);
    }

    private boolean resolveActive(Boolean active) {
        return active == null || active;
    }

    private int resolveDisplayOrder(Integer displayOrder) {
        return displayOrder == null ? 0 : Math.max(displayOrder, 0);
    }

    private BlogPostDTO toPublicDto(BlogPost post) {
        String normalizedContent = normalizeContent(post.getContent(), post.getExcerpt());
        return new BlogPostDTO(
                post.getTitle(),
                post.getSlug(),
                post.getExcerpt(),
                normalizedContent,
                normalizeImageUrl(post.getImageUrl())
        );
    }

    private BlogPostAdminResponseDTO toAdminDto(BlogPost post) {
        String normalizedContent = normalizeContent(post.getContent(), post.getExcerpt());
        return new BlogPostAdminResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getExcerpt(),
                normalizedContent,
                normalizeImageUrl(post.getImageUrl()),
                post.isActive(),
                post.getDisplayOrder()
        );
    }

    private String normalizeImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }
        return imageUrl.trim();
    }

    private String normalizeContent(String content, String excerpt) {
        if (content == null || content.isBlank()) {
            return excerpt == null ? "" : excerpt.trim();
        }
        return content.trim();
    }
}

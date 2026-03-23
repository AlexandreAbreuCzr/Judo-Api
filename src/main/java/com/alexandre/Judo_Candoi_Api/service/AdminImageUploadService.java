package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.upload.AdminImageUploadResponseDTO;
import com.alexandre.Judo_Candoi_Api.infra.exceptions.ResourceNotFoundException;
import com.alexandre.Judo_Candoi_Api.model.UploadedImage;
import com.alexandre.Judo_Candoi_Api.repository.UploadedImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminImageUploadService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");
    private static final Set<String> ALLOWED_FOLDERS = Set.of("blog", "sponsors", "site", "gallery", "general");
    private static final DateTimeFormatter FILE_PREFIX_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final String IMAGE_PATH_PREFIX = "/api/v1/uploads/images/";

    private final UploadedImageRepository uploadedImageRepository;
    private final long maxUploadBytes;

    public AdminImageUploadService(
            UploadedImageRepository uploadedImageRepository,
            @Value("${app.upload.max-size-bytes:15728640}") long maxUploadBytes
    ) {
        this.uploadedImageRepository = uploadedImageRepository;
        this.maxUploadBytes = maxUploadBytes;
    }

    public AdminImageUploadResponseDTO uploadImage(MultipartFile file, String requestedFolder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Selecione uma imagem para enviar.");
        }

        if (file.getSize() > maxUploadBytes) {
            throw new IllegalArgumentException("Arquivo excede o tamanho maximo permitido.");
        }

        String extension = extractExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Formato invalido. Use JPG, PNG, WEBP ou GIF.");
        }

        String detectedContentType = file.getContentType();
        if (detectedContentType == null || !detectedContentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("Apenas arquivos de imagem sao permitidos.");
        }

        String folder = normalizeFolder(requestedFolder);
        String fileName = buildFileName(extension);
        String contentType = normalizeContentType(detectedContentType, extension);
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException ex) {
            throw new IllegalStateException("Nao foi possivel processar a imagem enviada.", ex);
        }

        UploadedImage uploadedImage = new UploadedImage(
                fileName,
                folder,
                contentType,
                extension,
                bytes,
                bytes.length
        );

        UploadedImage persistedImage = uploadedImageRepository.save(uploadedImage);
        String url = IMAGE_PATH_PREFIX + persistedImage.getId() + "/" + fileName;
        return new AdminImageUploadResponseDTO(url, fileName, bytes.length);
    }

    public StoredImagePayload getImageById(Long imageId) {
        UploadedImage image = uploadedImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem nao encontrada para id: " + imageId));

        return new StoredImagePayload(
                image.getFileName(),
                image.getContentType(),
                image.getData(),
                image.getSizeInBytes()
        );
    }

    private String normalizeFolder(String requestedFolder) {
        if (requestedFolder == null || requestedFolder.isBlank()) {
            return "general";
        }

        String folder = requestedFolder.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9-]", "");
        if (!ALLOWED_FOLDERS.contains(folder)) {
            return "general";
        }
        return folder;
    }

    private String buildFileName(String extension) {
        String prefix = LocalDateTime.now().format(FILE_PREFIX_FORMATTER);
        String randomPart = UUID.randomUUID().toString().replace("-", "");
        return prefix + "-" + randomPart + "." + extension;
    }

    private String extractExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT).trim();
    }

    private String normalizeContentType(String providedContentType, String extension) {
        if (providedContentType != null && !providedContentType.isBlank()) {
            return providedContentType.trim();
        }

        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }

    public record StoredImagePayload(
            String fileName,
            String contentType,
            byte[] bytes,
            long sizeInBytes
    ) {
    }
}

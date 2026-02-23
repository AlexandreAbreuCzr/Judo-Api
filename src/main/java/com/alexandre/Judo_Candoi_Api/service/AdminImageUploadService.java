package com.alexandre.Judo_Candoi_Api.service;

import com.alexandre.Judo_Candoi_Api.dto.upload.AdminImageUploadResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private final Path uploadRoot;
    private final long maxUploadBytes;

    public AdminImageUploadService(
            @Value("${app.upload.dir:uploads}") String uploadDir,
            @Value("${app.upload.max-size-bytes:15728640}") long maxUploadBytes
    ) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
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

        String contentType = file.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("Apenas arquivos de imagem sao permitidos.");
        }

        String folder = normalizeFolder(requestedFolder);
        String fileName = buildFileName(extension);
        Path folderPath = uploadRoot.resolve(folder);
        Path targetPath = folderPath.resolve(fileName).normalize();

        if (!targetPath.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Destino de upload invalido.");
        }

        try {
            Files.createDirectories(folderPath);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Nao foi possivel salvar a imagem enviada.", ex);
        }

        String url = "/uploads/" + folder + "/" + fileName;
        return new AdminImageUploadResponseDTO(url, fileName, file.getSize());
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
}


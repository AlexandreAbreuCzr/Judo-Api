package com.alexandre.Judo_Candoi_Api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_images")
public class UploadedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 30)
    private String folder;

    @Column(nullable = false, length = 80)
    private String contentType;

    @Column(nullable = false, length = 12)
    private String extension;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private long sizeInBytes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected UploadedImage() {
    }

    public UploadedImage(
            String fileName,
            String folder,
            String contentType,
            String extension,
            byte[] data,
            long sizeInBytes
    ) {
        this.fileName = fileName;
        this.folder = folder;
        this.contentType = contentType;
        this.extension = extension;
        this.data = data;
        this.sizeInBytes = sizeInBytes;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolder() {
        return folder;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getData() {
        return data;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

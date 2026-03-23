package com.alexandre.Judo_Candoi_Api.repository;

import com.alexandre.Judo_Candoi_Api.model.UploadedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {
}

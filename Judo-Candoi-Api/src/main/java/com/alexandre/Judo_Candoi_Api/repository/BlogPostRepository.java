package com.alexandre.Judo_Candoi_Api.repository;

import com.alexandre.Judo_Candoi_Api.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findAllByActiveTrueOrderByDisplayOrderAscIdAsc();
}

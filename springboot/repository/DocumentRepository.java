// src/main/java/com/example/xsxk/repository/DocumentRepository.java
package com.example.xsxk.repository;

import com.example.xsxk.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Page<Document> findByUserId(Long userId, Pageable pageable);
}
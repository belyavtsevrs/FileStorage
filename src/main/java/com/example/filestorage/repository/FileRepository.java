package com.example.filestorage.repository;

import com.example.filestorage.storage.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
}

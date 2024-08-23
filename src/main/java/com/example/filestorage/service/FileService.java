package com.example.filestorage.service;

import com.example.filestorage.storage.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {
    FileEntity save(FileEntity fileEntity);
    FileEntity save(MultipartFile data,String description);
    List<FileEntity> findAll(int page,int size ,boolean groupBy);
    FileEntity findById(Long id);
    FileEntity toEntity(MultipartFile multipartFile);

}

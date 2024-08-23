package com.example.filestorage.service.Impl;

import com.example.filestorage.exceptions.FileNotFoundException;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.storage.FileEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {
    @Mock
    private FileRepository fileRepository;
    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    void save() {
        FileEntity file = new FileEntity();
        file.setTitle("TEST");

        when(fileRepository.save(any(FileEntity.class))).thenReturn(file);

        FileEntity saved = fileService.save(file);

        assertNotNull(saved);
        assertEquals("TEST",saved.getTitle());
    }

    @Test
    void findById_Success() {
        Long id = 1l;
        FileEntity file = new FileEntity();
        file.setId(id);

        when(fileRepository.findById(id)).thenReturn(Optional.of(file));

        FileEntity fileEntity = fileService.findById(id);

        assertNotNull(fileEntity);
        assertEquals(id,fileEntity.getId());
    }

    @Test
    void findById_NotFound() {
        Long id = 1l;
        when(fileRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(FileNotFoundException.class , () ->{
            fileService.findById(id);
        });
        assertNotNull("File with id %s not found",ex.getMessage());
    }

    @Test
    void findAll() {
        List<FileEntity> list = List.of(new FileEntity(), new FileEntity());

        when(fileRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(list));

        assertNotNull(list);

        List<FileEntity> listRes = fileService.findAll(0,10,true);

        assertNotNull(listRes);
    }
}
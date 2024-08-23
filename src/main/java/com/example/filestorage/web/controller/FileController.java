package com.example.filestorage.web.controller;

import com.example.filestorage.service.FileService;
import com.example.filestorage.storage.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload-file-json")
    public ResponseEntity<Long> uploadFile(@RequestBody FileEntity file){
        FileEntity fileEntity = fileService.save(file);
        return ResponseEntity.ok().body(fileEntity.getId());
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") @RequestBody MultipartFile data,
                                        @RequestParam(value = "description" ,
                                                      required = false,
                                                      defaultValue = "") String description){
        FileEntity file = fileService.save(data,description);
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<?> findFileById(@RequestParam("id") Long id){
        FileEntity file = fileService.findById(id);
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<FileEntity>> findAll(
            @RequestParam(defaultValue = "0",value = "page") int page,
            @RequestParam(defaultValue = "10",value = "size") int size,
            @RequestParam(defaultValue = "false",value = "groupBy") boolean groupBy
    ){
        List<FileEntity> res = fileService.findAll(page, size, groupBy);
        return ResponseEntity.ok().body(res);
    }
}

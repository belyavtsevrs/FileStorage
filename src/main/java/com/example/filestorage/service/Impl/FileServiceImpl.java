package com.example.filestorage.service.Impl;

import com.example.filestorage.exceptions.FileNotFoundException;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.storage.FileEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // На случай если данные будут заданых в формате JSON */
    @Override
    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    /*Загрузка файла
    *   data - файл который должен быть указан в качестве параметра
    *   description - описание файла
    * */
    @Override
    public FileEntity save(MultipartFile data,String description) {
        FileEntity file = toEntity(data);

        file.setDescription(description);

        file.setCreationDate(LocalDateTime.now());

        return fileRepository.save(file);
    }
    // Поиск файла по Id
    @Override
    public FileEntity findById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() ->
                        new FileNotFoundException(String.format("File with id %s not found", id))
                );
    }
    /* Получение списка всех файлов с учетом пагинации и сортировкой по дате
    *  page - страница,
    *  size - размер страницы,
    *  groupBy - сортировка
    * */
    public List<FileEntity> findAll(int page,int size ,boolean groupBy) {

        Sort.Direction direction = groupBy ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest request = PageRequest.of(page,size,Sort.by(direction,"creationDate"));

        return Optional.ofNullable(fileRepository.findAll(request).toList())
                .orElseThrow(()->
                        new FileNotFoundException("Is empty")
                );
    }

    // Конвертация файла в объект для последующего сохарения в БД
    @Override
    public FileEntity toEntity(MultipartFile multipartFile) {
        try {
            FileEntity file = new FileEntity();
            file.setTitle(multipartFile.getOriginalFilename());
            file.setData(multipartFile.getBytes());
            return file;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}

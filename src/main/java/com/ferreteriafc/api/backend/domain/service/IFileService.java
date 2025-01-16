package com.ferreteriafc.api.backend.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    String uploadFile(MultipartFile file);

}

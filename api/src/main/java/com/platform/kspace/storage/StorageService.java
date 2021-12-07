package com.platform.kspace.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  String store(MultipartFile file);

  String store(String base64Image);

  void delete(String fileName);

  void deleteAll();

  void init();
}
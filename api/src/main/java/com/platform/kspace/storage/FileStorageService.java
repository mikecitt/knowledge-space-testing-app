package com.platform.kspace.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import com.platform.kspace.exceptions.StorageException;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

  private final Path root = Paths.get("src/main/resources/static/uploads");

  private static String notSupportedResponse = "Storage file is not supported";
  private static String notValidResponse = "Storage file is not valid";

  @Override
  public String store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file");
      }
      String originalFilename = file.getOriginalFilename();
      if (originalFilename != null) {
        originalFilename = java.util.UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
      }
      Path destinationFile = this.root.resolve(Paths.get(originalFilename)).normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.root.toAbsolutePath())) {
        throw new StorageException("Cannot store file outside current directory");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      return originalFilename;
    } catch (IOException e) {
      throw new StorageException("Failed to store file");
    }
  }

  @Override
  public String store(String base64Image) {
    checkFileType(base64Image);
    String extension = getExtension(base64Image);
    byte[] decodedImage = getDecodedImage(base64Image);

    try {
      String originalFilename = java.util.UUID.randomUUID() + "." + extension;

      Path destinationFile = this.root.resolve(Paths.get(originalFilename)).normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.root.toAbsolutePath())) {
        throw new StorageException("Cannot store file outside current directory");
      }

      try (FileOutputStream output = new FileOutputStream(destinationFile.toFile())) {
        output.write(decodedImage);
      }

      return originalFilename;
    } catch (Exception e) {
      throw new StorageException("Failed to store file");
    }
  }

  @Override
  public void delete(String fileName) {
    try {
      FileSystemUtils.deleteRecursively(this.root.resolve(fileName));
    } catch (IOException e) {
      throw new StorageException("Failed to delete file");
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage");
    }
  }

  private String getExtension(String base64Image) {
    String extension = null;

    try {
      extension = base64Image.substring(base64Image.indexOf("/") + 1, base64Image.indexOf(";"));

      if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
        throw new StorageException(notSupportedResponse);
      }
    } catch (IndexOutOfBoundsException ex) {
      throw new StorageException(notValidResponse);
    }

    return extension;
  }

  private byte[] getDecodedImage(String base64Image) {
    try {
      String encodedImage = base64Image.substring(base64Image.indexOf(",") + 1);

      return Base64.getDecoder().decode(encodedImage);
    } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
      throw new StorageException(notValidResponse);
    }
  }

  private void checkFileType(String base64Image) {
    try {
      String fileType = base64Image.substring(base64Image.indexOf(":") + 1, base64Image.indexOf("/"));

      if (!fileType.equals("image")) {
        throw new StorageException(notSupportedResponse);
      }
    } catch (IndexOutOfBoundsException ex) {
      throw new StorageException(notValidResponse);
    }
  }
}
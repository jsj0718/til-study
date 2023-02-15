package me.jsj.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileService {
    @Value("${file.download-dir}")
    private String downloadPath;

    public Resource loadFile() {
        try {
            Path filePath = Paths.get(downloadPath).toAbsolutePath().normalize().resolve("template.csv");
            log.info(filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new IllegalArgumentException("File Not Found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

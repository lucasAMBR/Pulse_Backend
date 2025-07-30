package com.LHDev.PulseChating.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

    public static String saveImage(MultipartFile file) throws IOException {
        Path uploadPath =  Paths.get("uploads");
        String publicUrlPrefix = "/profile_pic/";

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = System.currentTimeMillis() + fileExtension;

        Path targetLocation = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return publicUrlPrefix + filename;
    }

    public static boolean validateImage(MultipartFile image){
        long maxSizeInBytes = 4 * 1024 * 1024;

        if(image.getSize() < maxSizeInBytes){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null || !contentType.matches("image/(png|jpeg|jpg|webp)")) {
            return false;
        }

        return true;
    }
}

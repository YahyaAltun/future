package com.future.future.service;

import com.future.future.domain.ImageFile;
import com.future.future.exception.ImageFileException;
import com.future.future.exception.ResourceNotFoundException;
import com.future.future.exception.message.ErrorMessage;
import com.future.future.repository.ImageFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImageFileService {

    private ImageFileRepository imageFileRepository;

    public String saveImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ImageFile imageFile = null;
        try {
            imageFile = new ImageFile(fileName, file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new ImageFileException(e.getMessage());
        }
        imageFileRepository.save(imageFile);

        return imageFile.getId();
    }

    public ImageFile getImageById(String id){
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,id)));

        return  imageFile;
    }

}

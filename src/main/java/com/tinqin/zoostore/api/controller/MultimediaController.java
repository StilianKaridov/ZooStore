package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.CloudinaryMultimedia;
import com.tinqin.zoostore.api.request.MultimediaUploadRequest;
import com.tinqin.zoostore.data.entity.Multimedia;
import com.tinqin.zoostore.exception.MissingFileException;
import com.tinqin.zoostore.exception.NullOrEmptyStringException;
import com.tinqin.zoostore.exception.UnsupportedFileTypeException;
import com.tinqin.zoostore.service.MultimediaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultimediaController {

    // TODO Remove saving in the database. Fetch the images/videos with url/publicId directly from the cloud

    private static final String SUCCESSFULLY_UPLOADED_MESSAGE = "Successfully uploaded %s!";
    private static final String SUCCESSFULLY_DELETED_MESSAGE = "Successfully deleted!";
    private static final String NOT_EXISTING_MULTIMEDIA_MESSAGE = "No multimedia with public ID %s";

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    @PostMapping("/uploadMultimedia")
    public ResponseEntity<String> upload(MultimediaUploadRequest fileUploadDTO) throws IOException {

        if (fileUploadDTO.getFile().isEmpty()) {
            throw new MissingFileException();
        }

        Multimedia multimedia = createMultimediaEntity(fileUploadDTO.getFile());

        this.multimediaService.saveMultimediaToRepository(multimedia);

        return ResponseEntity.
                status(201).
                body(
                        String.format(SUCCESSFULLY_UPLOADED_MESSAGE, multimedia.getType())
                );
    }

    @Transactional
    @DeleteMapping("/deleteMultimedia")
    public ResponseEntity<String> delete(@RequestParam(name = "public_id") String publicId) {
        if (publicId == null || publicId.trim().equals("")) {
            throw new NullOrEmptyStringException();
        }

        boolean isDeletedFromCloud = this.multimediaService.deleteMultimediaFromCloudinary(publicId);
        boolean isDeletedFromRepo = this.multimediaService.deleteMultimediaFromRepository(publicId);

        if (isDeletedFromCloud && isDeletedFromRepo) {
            return ResponseEntity.ok(SUCCESSFULLY_DELETED_MESSAGE);
        }

        return ResponseEntity.
                status(404).
                body(
                        String.format(NOT_EXISTING_MULTIMEDIA_MESSAGE, publicId)
                );
    }

    private Multimedia createMultimediaEntity(MultipartFile file) throws IOException {
        final String videoLabel = "video";
        final String imageLabel = "image";

        Multimedia multimedia = new Multimedia();

        if (file.getContentType().contains(videoLabel)) {
            multimedia.setType(videoLabel);
        } else if (file.getContentType().contains(imageLabel)) {
            multimedia.setType(imageLabel);
        } else {
            throw new UnsupportedFileTypeException();
        }

        final CloudinaryMultimedia uploadedFile = this.multimediaService.uploadMultimedia(file);

        multimedia.setPublicId(uploadedFile.getPublicId());
        multimedia.setUrl(uploadedFile.getUrl());

        return multimedia;
    }

    @ExceptionHandler(value = MissingFileException.class)
    public ResponseEntity<String> handleMissingFileException(MissingFileException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NullOrEmptyStringException.class)
    public ResponseEntity<String> handleNullOrEmptyPublicIdException(NullOrEmptyStringException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = UnsupportedFileTypeException.class)
    public ResponseEntity<String> handleUnsupportedFileTypeException(UnsupportedFileTypeException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ex.getMessage());
    }
}

package com.tinqin.zoostore.core.multimedia;

import com.cloudinary.Cloudinary;
import com.tinqin.zoostore.core.exception.multimedia.UnsupportedFileTypeException;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadOperation;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadRequest;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadResponse;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.repository.MultimediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class MultimediaUploadOperationProcessor implements MultimediaUploadOperation {

    private static final String TEMP_FILE_LABEL = "temp-file";

    private static final String PUBLIC_ID_LABEL = "public_id";
    private static final String PUBLIC_ID_VALUE = "";

    private static final String RESOURCE_TYPE_LABEL = "resource_type";
    private static final String RESOURCE_TYPE_VALUE = "auto";

    private static final String URL_LABEL = "url";
    private static final String URL_DEFAULT_VALUE = "https://howfix.net/wp-content/uploads/2018/02/sIaRmaFSMfrw8QJIBAa8mA-article.png";

    private final Cloudinary cloudinary;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MultimediaUploadOperationProcessor(Cloudinary cloudinary, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.cloudinary = cloudinary;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public MultimediaUploadResponse process(MultimediaUploadRequest input) {
        MultipartFile file = input.getFile();

        String fileResourceType = this.getFileResourceType(file);

        File tempFile;
        try {
            tempFile = File.createTempFile(TEMP_FILE_LABEL, file.getOriginalFilename());
            file.transferTo(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        Multimedia multimedia;

        try {

            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = this.cloudinary.
                    uploader().
                    upload(tempFile, Map.of(
                            RESOURCE_TYPE_LABEL, RESOURCE_TYPE_VALUE
                    ));

            String url = uploadResult.getOrDefault(URL_LABEL, URL_DEFAULT_VALUE);
            String publicId = uploadResult.getOrDefault(PUBLIC_ID_LABEL, PUBLIC_ID_VALUE);

            multimedia = Multimedia
                    .builder()
                    .type(fileResourceType)
                    .url(url)
                    .publicId(publicId)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            tempFile.delete();
        }

        this.multimediaRepository.save(multimedia);

        return this.modelMapper.map(multimedia, MultimediaUploadResponse.class);
    }

    private String getFileResourceType(MultipartFile file) {
        final String videoLabel = "video";
        final String imageLabel = "image";

        if (file.getContentType().contains(videoLabel)) {
            return videoLabel;
        } else if (file.getContentType().contains(imageLabel)) {
            return imageLabel;
        } else {
            throw new UnsupportedFileTypeException();
        }
    }
}

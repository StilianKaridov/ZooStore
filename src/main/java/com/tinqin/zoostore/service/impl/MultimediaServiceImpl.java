package com.tinqin.zoostore.service.impl;

import com.cloudinary.Cloudinary;
import com.tinqin.zoostore.api.request.multimedia.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.multimedia.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaUploadResponse;
import com.tinqin.zoostore.data.entity.Multimedia;
import com.tinqin.zoostore.data.repository.MultimediaRepository;
import com.tinqin.zoostore.exception.multimedia.MultimediaDeletionException;
import com.tinqin.zoostore.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.exception.multimedia.UnsupportedFileTypeException;
import com.tinqin.zoostore.service.MultimediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MultimediaServiceImpl implements MultimediaService {

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
    public MultimediaServiceImpl(Cloudinary cloudinary, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.cloudinary = cloudinary;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MultimediaRetrieveResponse retrieveMultimedia(String publicId) {
        Optional<Multimedia> multimediaOpt = this.multimediaRepository.findByPublicId(publicId);

        if(multimediaOpt.isEmpty()){
            throw new NoSuchMultimediaException();
        }

        return this.modelMapper.map(multimediaOpt.get(), MultimediaRetrieveResponse.class);
    }

    @Override
    public MultimediaUploadResponse uploadMultimedia(MultimediaUploadRequest multimediaUploadRequest) throws IOException {
        MultipartFile file = multimediaUploadRequest.getFile();

        String fileResourceType = this.getFileResourceType(file);

        File tempFile = File.createTempFile(TEMP_FILE_LABEL, file.getOriginalFilename());
        file.transferTo(tempFile);

        Multimedia multimedia = new Multimedia();
        multimedia.setType(fileResourceType);

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = this.cloudinary.
                    uploader().
                    upload(tempFile, Map.of(
                            RESOURCE_TYPE_LABEL, RESOURCE_TYPE_VALUE
                    ));

            String url = uploadResult.getOrDefault(URL_LABEL, URL_DEFAULT_VALUE);
            String publicId = uploadResult.getOrDefault(PUBLIC_ID_LABEL, PUBLIC_ID_VALUE);

            multimedia.setUrl(url);
            multimedia.setPublicId(publicId);
        } finally {
            tempFile.delete();
        }

        this.multimediaRepository.save(multimedia);

        return this.modelMapper.map(multimedia, MultimediaUploadResponse.class);
    }

    @Override
    public MultimediaDeleteResponse deleteMultimedia(String publicId) {
        Optional<Multimedia> multimediaOpt = this.multimediaRepository.findByPublicId(publicId);

        if (multimediaOpt.isEmpty()) {
            throw new NoSuchMultimediaException();
        }

        Map<String, String> params = new HashMap<>();
        params.put(RESOURCE_TYPE_LABEL, multimediaOpt.get().getType());

        try {
            this.cloudinary.uploader().destroy(publicId, params);
        } catch (IOException e) {
            throw new MultimediaDeletionException();
        }

        this.multimediaRepository.deleteByPublicId(publicId);

        return this.modelMapper.map(multimediaOpt.get(), MultimediaDeleteResponse.class);
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

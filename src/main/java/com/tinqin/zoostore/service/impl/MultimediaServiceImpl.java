package com.tinqin.zoostore.service.impl;

import com.cloudinary.Cloudinary;
import com.tinqin.zoostore.api.request.CloudinaryMultimedia;
import com.tinqin.zoostore.data.entity.Multimedia;
import com.tinqin.zoostore.data.repository.MultimediaRepository;
import com.tinqin.zoostore.service.MultimediaService;
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

    @Autowired
    public MultimediaServiceImpl(Cloudinary cloudinary, MultimediaRepository multimediaRepository) {
        this.cloudinary = cloudinary;
        this.multimediaRepository = multimediaRepository;
    }

    @Override
    public CloudinaryMultimedia uploadMultimedia(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE_LABEL, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        CloudinaryMultimedia multimedia = new CloudinaryMultimedia();

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

        return multimedia;
    }

    @Override
    public void saveMultimediaToRepository(Multimedia multimedia) {
        this.multimediaRepository.save(multimedia);
    }

    @Override
    public boolean deleteMultimediaFromRepository(String publicId) {
        if (this.multimediaRepository.findByPublicId(publicId).isEmpty()) {
            return false;
        }

        this.multimediaRepository.deleteByPublicId(publicId);

        return true;
    }

    @Override
    public boolean deleteMultimediaFromCloudinary(String publicId) {
        Optional<Multimedia> multimediaOpt = this.multimediaRepository.findByPublicId(publicId);

        if (multimediaOpt.isEmpty()) {
            return false;
        }

        Map<String, String> params = new HashMap<>();
        params.put(RESOURCE_TYPE_LABEL, multimediaOpt.get().getType());

        try {
            this.cloudinary.uploader().destroy(publicId, params);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}

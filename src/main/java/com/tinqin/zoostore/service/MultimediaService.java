package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.CloudinaryMultimedia;
import com.tinqin.zoostore.data.entity.Multimedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultimediaService {

    CloudinaryMultimedia uploadMultimedia(MultipartFile multipartFile) throws IOException;

    void saveMultimediaToRepository(Multimedia multimedia);

    boolean deleteMultimediaFromRepository(String publicId);

    boolean deleteMultimediaFromCloudinary(String publicId);
}

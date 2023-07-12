package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.multimedia.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.multimedia.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaUploadResponse;

import java.io.IOException;

public interface MultimediaService {

    MultimediaRetrieveResponse retrieveMultimedia(String publicId);

    MultimediaUploadResponse uploadMultimedia(MultimediaUploadRequest multimedia) throws IOException;

    MultimediaDeleteResponse deleteMultimedia(String publicId);
}

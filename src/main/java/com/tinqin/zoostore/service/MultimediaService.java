package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.MultimediaUploadResponse;

import java.io.IOException;

public interface MultimediaService {

    MultimediaRetrieveResponse retrieveMultimedia(String publicId);

    MultimediaUploadResponse uploadMultimedia(MultimediaUploadRequest multimedia) throws IOException;

    MultimediaDeleteResponse deleteMultimedia(String publicId);
}

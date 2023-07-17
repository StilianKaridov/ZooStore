package com.tinqin.zoostore.core.multimedia;

import com.cloudinary.Cloudinary;
import com.tinqin.zoostore.api.exception.multimedia.MultimediaDeletionException;
import com.tinqin.zoostore.api.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteOperation;
import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteRequest;
import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteResponse;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.repository.MultimediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MultimediaDeleteOperationProcessor implements MultimediaDeleteOperation {

    private static final String RESOURCE_TYPE_LABEL = "resource_type";

    private final Cloudinary cloudinary;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MultimediaDeleteOperationProcessor(Cloudinary cloudinary, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.cloudinary = cloudinary;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MultimediaDeleteResponse process(MultimediaDeleteRequest input) {
        String publicId = input.getPublicId();

        Multimedia multimedia = this.multimediaRepository
                .findByPublicId(publicId)
                .orElseThrow(NoSuchMultimediaException::new);

        Map<String, String> params = new HashMap<>();
        params.put(RESOURCE_TYPE_LABEL, multimedia.getType());

        try {
            this.cloudinary.uploader().destroy(publicId, params);
        } catch (IOException e) {
            throw new MultimediaDeletionException();
        }

        this.multimediaRepository.deleteByPublicId(publicId);

        return this.modelMapper.map(multimedia, MultimediaDeleteResponse.class);
    }
}

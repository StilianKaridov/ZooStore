package com.tinqin.zoostore.core.multimedia;

import com.tinqin.zoostore.api.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveOperation;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveRequest;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveResponse;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.repository.MultimediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaRetrieveOperationProcessor implements MultimediaRetrieveOperation {

    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MultimediaRetrieveOperationProcessor(MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MultimediaRetrieveResponse process(MultimediaRetrieveRequest input) {
        Multimedia multimedia = this.multimediaRepository
                .findByPublicId(input.getPublicId())
                .orElseThrow(NoSuchMultimediaException::new);

        return this.modelMapper.map(multimedia, MultimediaRetrieveResponse.class);
    }
}

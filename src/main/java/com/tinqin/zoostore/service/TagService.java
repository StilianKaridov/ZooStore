package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.TagCreateRequest;
import com.tinqin.zoostore.api.request.TagUpdateRequest;
import com.tinqin.zoostore.api.response.TagCreateResponse;
import com.tinqin.zoostore.api.response.UpdateTagResponse;

public interface TagService {

    TagCreateResponse createTag(TagCreateRequest tagCreateRequest);

    UpdateTagResponse updateTag(TagUpdateRequest tagUpdateRequest);
}

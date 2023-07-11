package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.TagCreateRequest;
import com.tinqin.zoostore.api.request.TagUpdateRequest;
import com.tinqin.zoostore.api.response.TagArchiveResponse;
import com.tinqin.zoostore.api.response.TagCreateResponse;
import com.tinqin.zoostore.api.response.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.TagUpdateResponse;

public interface TagService {

    TagCreateResponse createTag(TagCreateRequest tagCreateRequest);

    TagUpdateResponse updateTag(TagUpdateRequest tagUpdateRequest);

    TagArchiveResponse archiveTag(String title);

    TagUnarchiveResponse  unarchiveTag(String title);
}

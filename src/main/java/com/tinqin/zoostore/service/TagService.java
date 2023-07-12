package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.tag.TagCreateRequest;
import com.tinqin.zoostore.api.request.tag.TagUpdateRequest;
import com.tinqin.zoostore.api.response.tag.TagArchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagCreateResponse;
import com.tinqin.zoostore.api.response.tag.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagUpdateResponse;

public interface TagService {

    TagCreateResponse createTag(TagCreateRequest tagCreateRequest);

    TagUpdateResponse updateTag(TagUpdateRequest tagUpdateRequest);

    TagArchiveResponse archiveTag(String title);

    TagUnarchiveResponse  unarchiveTag(String title);
}

package com.tinqin.zoostore.restexport;

import com.tinqin.zoostore.api.operations.item.get.GetItemByIdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface ZooStoreRestClient {

    @RequestLine("GET /getItem/{id}")
    GetItemByIdResponse getItemById(@Param String id);
}

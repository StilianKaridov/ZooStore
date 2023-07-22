package com.tinqin.zoostore.restexport;

import com.tinqin.zoostore.api.operations.item.get.GetItemByIdResponse;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Headers({"Content-Type: application/json"})
public interface ZooStoreRestClient {

    @RequestLine("GET /getItem/{id}")
    GetItemByIdResponse getItemById(@PathVariable UUID id);
}

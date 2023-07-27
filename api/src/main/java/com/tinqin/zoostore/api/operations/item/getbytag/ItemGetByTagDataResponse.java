package com.tinqin.zoostore.api.operations.item.getbytag;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetByTagDataResponse implements OperationResponse {

    private String id;

    private String title;

    private String description;

    private VendorGetResponse vendor;

    private Set<MultimediaGetResponse> multimedia;

    private Set<TagGetResponse> tags;
}

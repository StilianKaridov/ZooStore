package com.tinqin.zoostore.api.operations.item.getbytag;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetByTagRequest implements OperationRequest {

    private String tagTitle;

    private Integer pageNumber;

    private Integer pageSize;
}

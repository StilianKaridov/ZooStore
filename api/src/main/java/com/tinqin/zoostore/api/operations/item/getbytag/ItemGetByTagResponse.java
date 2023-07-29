package com.tinqin.zoostore.api.operations.item.getbytag;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetByTagResponse implements OperationResponse {

    private Integer page;

    private Integer limit;

    private Long totalItems;

    private List<ItemGetByTagDataResponse> items;
}

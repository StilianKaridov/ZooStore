package com.tinqin.zoostore.api.operations.item.getlistofitems;

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
public class GetListOfItemsResponse implements OperationResponse {

    private List<ItemGetListOfItemsDataResponse> items;
}

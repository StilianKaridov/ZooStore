package com.tinqin.zoostore.api.operations.item.getlistofitems;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
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
public class GetListOfItemsRequest implements OperationRequest {

    private List<String> ids;
}

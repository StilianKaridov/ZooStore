package com.tinqin.zoostore.api.operations.item.getlistofitems;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.VendorGetResponse;
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
public class ItemGetListOfItemsDataResponse implements OperationResponse {

    private String id;

    private String title;

    private VendorGetResponse vendor;
}

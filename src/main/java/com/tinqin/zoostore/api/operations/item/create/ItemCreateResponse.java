package com.tinqin.zoostore.api.operations.item.create;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCreateResponse implements OperationResponse {

    private String title;

    private String description;

    private String vendorId;
}

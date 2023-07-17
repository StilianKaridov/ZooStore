package com.tinqin.zoostore.api.operations.vendor.delete;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorDeleteResponse implements OperationResponse {

    private String name;

    private String phoneNumber;
}

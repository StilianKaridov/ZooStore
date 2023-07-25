package com.tinqin.zoostore.api.operations.vendor.update;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
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
public class VendorUpdateResponse implements OperationResponse {

    private String vendorId;

    private String name;

    private String phoneNumber;
}

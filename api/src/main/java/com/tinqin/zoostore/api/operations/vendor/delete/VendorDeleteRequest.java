package com.tinqin.zoostore.api.operations.vendor.delete;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
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
public class VendorDeleteRequest implements OperationRequest {

    @NotBlank(message = "The name must not be empty!")
    private String vendorName;
}

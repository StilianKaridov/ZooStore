package com.tinqin.zoostore.api.operations.vendor.update.phone;

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
public class VendorUpdatePhoneRequest implements OperationRequest {

    @NotBlank(message = "The old phone must not be empty!")
    private String oldPhone;

    @NotBlank(message = "The new phone must not be empty!")
    private String newPhone;
}

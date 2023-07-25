package com.tinqin.zoostore.api.operations.vendor.update;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class VendorUpdateRequest implements OperationRequest {

    @NotBlank(message = "Vendor id must not be empty!")
    private String vendorId;

    private Optional<String> name = Optional.empty();

    private Optional<String> phoneNumber = Optional.empty();
}

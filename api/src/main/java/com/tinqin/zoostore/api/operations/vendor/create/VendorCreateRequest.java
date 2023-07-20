package com.tinqin.zoostore.api.operations.vendor.create;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class VendorCreateRequest implements OperationRequest {

    @NotBlank(message = "The name must not be empty!")
    private String name;

    @Pattern(
            regexp = "(\\+)?(359|0)8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}",
            message = "Invalid phone number format!"
    )
    @NotBlank(message = "The phone number must not be empty!")
    private String phoneNumber;
}

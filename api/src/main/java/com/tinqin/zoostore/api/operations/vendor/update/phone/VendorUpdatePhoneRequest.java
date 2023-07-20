package com.tinqin.zoostore.api.operations.vendor.update.phone;

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
public class VendorUpdatePhoneRequest implements OperationRequest {

    @Pattern(
            regexp = "(\\+)?(359|0)8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}",
            message = "Invalid phone number format!"
    )
    @NotBlank(message = "The old phone must not be empty!")
    private String oldPhone;

    @Pattern(
            regexp = "(\\+)?(359|0)8[789]\\d{1}(|-| )\\d{3}(|-| )\\d{3}",
            message = "Invalid phone number format!"
    )
    @NotBlank(message = "The new phone must not be empty!")
    private String newPhone;
}

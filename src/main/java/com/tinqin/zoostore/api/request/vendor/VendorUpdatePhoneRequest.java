package com.tinqin.zoostore.api.request.vendor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorUpdatePhoneRequest {

    @NotBlank(message = "The old phone must not be empty!")
    private String oldPhone;

    @NotBlank(message = "The new phone must not be empty!")
    private String newPhone;
}

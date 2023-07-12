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
public class VendorCreateRequest {

    @NotBlank(message = "The name must not be empty!")
    private String name;

    @NotBlank(message = "The phone number must not be empty!")
    private String phoneNumber;
}

package com.tinqin.zoostore.api.request.vendor;

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
public class VendorCreateRequest {

    @NotBlank(message = "The name must not be empty!")
    private String name;

    @NotBlank(message = "The phone number must not be empty!")
    private String phoneNumber;
}

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
public class VendorUpdateNameRequest {

    @NotBlank(message = "The old name must not be empty!")
    private String oldName;

    @NotBlank(message = "The new name must not be empty!")
    private String newName;
}
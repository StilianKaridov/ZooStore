package com.tinqin.zoostore.api.request.item;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCreateRequest {

    @NotBlank(message = "The title must not be empty!")
    private String title;

    @NotBlank(message = "The description must not be empty!")
    private String description;

    @NotBlank(message = "The vendorId must not be empty!")
    private String vendorId;
}

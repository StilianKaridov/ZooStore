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
public class ItemUpdateDescriptionRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotBlank(message = "The new description must not be empty!")
    private String newDescription;
}

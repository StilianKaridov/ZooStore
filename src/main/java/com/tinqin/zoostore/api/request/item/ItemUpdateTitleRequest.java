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
public class ItemUpdateTitleRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotBlank(message = "The new title must not be empty!")
    private String newTitle;
}

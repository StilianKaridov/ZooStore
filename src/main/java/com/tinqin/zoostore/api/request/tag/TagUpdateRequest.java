package com.tinqin.zoostore.api.request.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagUpdateRequest {

    @NotBlank(message = "The old title must not be empty!")
    private String oldTitle;

    @NotBlank(message = "The new title must not be empty!")
    private String newTitle;
}

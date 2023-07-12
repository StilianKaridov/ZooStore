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
public class TagCreateRequest {

    @NotBlank(message = "The title must not be empty!")
    private String title;
}

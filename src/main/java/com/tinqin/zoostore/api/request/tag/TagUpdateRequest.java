package com.tinqin.zoostore.api.request.tag;

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
public class TagUpdateRequest {

    @NotBlank(message = "The old title must not be empty!")
    private String oldTitle;

    @NotBlank(message = "The new title must not be empty!")
    private String newTitle;
}

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
public class TagCreateRequest {

    @NotBlank(message = "The title must not be empty!")
    private String title;
}

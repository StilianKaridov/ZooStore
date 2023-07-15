package com.tinqin.zoostore.api.request.item;

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
public class ItemUpdateTitleRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotBlank(message = "The new title must not be empty!")
    private String newTitle;
}

package com.tinqin.zoostore.api.request.item;

import com.tinqin.zoostore.utils.validator.EachNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemRemoveTagRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotEmpty(message = "The tagsId must not be empty!")
    @EachNotBlank
    private Set<String> tagsId;
}

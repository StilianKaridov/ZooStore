package com.tinqin.zoostore.api.operations.itemtag.add;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import com.tinqin.zoostore.api.validator.EachNotBlank;
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
public class ItemAddTagRequest implements OperationRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotEmpty(message = "The tagsId must not be empty!")
    @EachNotBlank
    private Set<String> tagsId;
}

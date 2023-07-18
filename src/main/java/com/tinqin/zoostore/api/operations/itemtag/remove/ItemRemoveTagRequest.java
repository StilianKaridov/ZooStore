package com.tinqin.zoostore.api.operations.itemtag.remove;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import com.tinqin.zoostore.core.validation.UuidValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class ItemRemoveTagRequest implements OperationRequest {

    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format!"
    )
    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotEmpty(message = "The tagsId must not be empty!")
    @UuidValidation
    private Set<String> tagsId;
}

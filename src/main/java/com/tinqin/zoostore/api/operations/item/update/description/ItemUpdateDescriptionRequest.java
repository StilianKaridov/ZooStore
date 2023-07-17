package com.tinqin.zoostore.api.operations.item.update.description;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
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
public class ItemUpdateDescriptionRequest implements OperationRequest {

    @NotBlank(message = "The id must not be empty!")
    private String id;

    @NotBlank(message = "The new description must not be empty!")
    private String newDescription;
}

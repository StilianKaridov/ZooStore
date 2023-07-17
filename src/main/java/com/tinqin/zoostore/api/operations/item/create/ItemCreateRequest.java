package com.tinqin.zoostore.api.operations.item.create;

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
public class ItemCreateRequest implements OperationRequest {

    @NotBlank(message = "The title must not be empty!")
    private String title;

    @NotBlank(message = "The description must not be empty!")
    private String description;

    @NotBlank(message = "The vendorId must not be empty!")
    private String vendorId;
}

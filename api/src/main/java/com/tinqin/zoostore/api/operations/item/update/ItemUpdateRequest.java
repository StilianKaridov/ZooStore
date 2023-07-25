package com.tinqin.zoostore.api.operations.item.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tinqin.zoostore.api.operations.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemUpdateRequest implements OperationRequest {

    @NotBlank(message = "Item id must not be empty!")
    private String itemId;

    private Optional<String> title = Optional.empty();

    private Optional<String> description = Optional.empty();
}

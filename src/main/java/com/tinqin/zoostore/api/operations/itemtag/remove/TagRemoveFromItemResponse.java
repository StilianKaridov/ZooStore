package com.tinqin.zoostore.api.operations.itemtag.remove;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagRemoveFromItemResponse implements OperationResponse {

    private UUID id;
}

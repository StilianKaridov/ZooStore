package com.tinqin.zoostore.api.operations.itemmultimedia.remove;

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
public class MultimediaRemoveFromItemResponse implements OperationResponse {

    private UUID id;
}

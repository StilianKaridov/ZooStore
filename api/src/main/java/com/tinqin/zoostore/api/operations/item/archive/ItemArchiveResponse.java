package com.tinqin.zoostore.api.operations.item.archive;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemArchiveResponse implements OperationResponse {

    private String id;

    private String title;

    private Boolean isArchived;
}

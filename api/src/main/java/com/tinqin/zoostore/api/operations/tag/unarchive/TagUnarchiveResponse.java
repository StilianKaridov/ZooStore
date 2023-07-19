package com.tinqin.zoostore.api.operations.tag.unarchive;

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
public class TagUnarchiveResponse implements OperationResponse {

    private UUID id;

    private String title;

    private Boolean isArchived;
}

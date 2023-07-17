package com.tinqin.zoostore.api.operations.item.update.title;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemUpdateTitleResponse implements OperationResponse {

    private String id;

    private String title;
}

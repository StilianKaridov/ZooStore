package com.tinqin.zoostore.api.operations.itemtag.add;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemAddTagResponse implements OperationResponse {

    private String id;

    private String title;

    private Set<TagAddToItemResponse> tags;
}

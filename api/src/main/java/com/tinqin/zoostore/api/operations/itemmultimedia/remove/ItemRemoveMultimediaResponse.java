package com.tinqin.zoostore.api.operations.itemmultimedia.remove;

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
public class ItemRemoveMultimediaResponse implements OperationResponse {

    private String id;

    private String title;

    private Set<MultimediaRemoveFromItemResponse> multimedia;
}

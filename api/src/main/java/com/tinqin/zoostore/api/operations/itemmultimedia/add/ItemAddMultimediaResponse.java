package com.tinqin.zoostore.api.operations.itemmultimedia.add;

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
public class ItemAddMultimediaResponse implements OperationResponse {

    private String id;

    private String title;

    private Set<MultimediaAddToItemResponse> multimedia;
}

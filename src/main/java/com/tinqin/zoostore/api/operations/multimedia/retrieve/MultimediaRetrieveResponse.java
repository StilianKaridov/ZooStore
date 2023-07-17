package com.tinqin.zoostore.api.operations.multimedia.retrieve;

import com.tinqin.zoostore.api.operations.base.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaRetrieveResponse implements OperationResponse {

    private String url;
}

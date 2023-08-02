package com.tinqin.zoostore.api.operations.item.getbytitle;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetByTitleRequest implements OperationRequest {

    private String title;

    private Integer pageNumber;

    private Integer pageSize;
}

package com.tinqin.zoostore.api.operations.vendor.unarchive;

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
public class VendorUnarchiveResponse implements OperationResponse {

    private UUID id;

    private String name;

    private String phoneNumber;

    private Boolean isArchived;
}

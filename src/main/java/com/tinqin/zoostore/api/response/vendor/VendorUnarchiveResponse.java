package com.tinqin.zoostore.api.response.vendor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorUnarchiveResponse {

    private UUID id;

    private String name;

    private String phoneNumber;

    private Boolean isArchived;
}

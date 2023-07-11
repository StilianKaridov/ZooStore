package com.tinqin.zoostore.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorCreateResponse {

    private UUID id;

    private String name;

    private String phoneNumber;
}

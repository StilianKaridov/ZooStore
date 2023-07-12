package com.tinqin.zoostore.api.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCreateResponse {

    private String title;

    private String description;

    private String vendorId;
}

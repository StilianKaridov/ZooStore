package com.tinqin.zoostore.api.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemUpdateTitleResponse {

    private String id;

    private String title;
}

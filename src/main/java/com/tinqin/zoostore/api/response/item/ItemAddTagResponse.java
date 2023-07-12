package com.tinqin.zoostore.api.response.item;

import com.tinqin.zoostore.api.response.tag.TagAddToItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemAddTagResponse {

    private String id;

    private String title;

    private Set<TagAddToItemResponse> tags;
}

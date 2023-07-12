package com.tinqin.zoostore.api.response.item;

import com.tinqin.zoostore.api.response.tag.TagRemoveFromItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemRemoveTagResponse {

    private String id;

    private String title;

    private Set<TagRemoveFromItemResponse> tags;
}

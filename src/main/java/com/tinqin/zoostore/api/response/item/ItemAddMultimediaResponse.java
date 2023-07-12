package com.tinqin.zoostore.api.response.item;

import com.tinqin.zoostore.api.response.multimedia.MultimediaAddToItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemAddMultimediaResponse {

    private String id;

    private String title;

    private Set<MultimediaAddToItemResponse> multimedia;
}

package com.tinqin.zoostore.api.response.item;

import com.tinqin.zoostore.api.response.multimedia.MultimediaRemoveFromItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemRemoveMultimediaResponse {

    private String id;

    private String title;

    private Set<MultimediaRemoveFromItemResponse> multimedia;
}

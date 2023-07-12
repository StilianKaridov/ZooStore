package com.tinqin.zoostore.api.response.multimedia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaUploadResponse {

    private String url;

    private String publicId;
}

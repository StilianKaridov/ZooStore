package com.tinqin.zoostore.api.request.multimedia;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class MultimediaRetrieveRequest {

    @NotBlank(message = "The public id must not be empty!")
    private String publicId;
}

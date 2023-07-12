package com.tinqin.zoostore.api.request.multimedia;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaDeleteRequest {

    @NotBlank(message = "The public id must not be empty!")
    private String publicId;
}

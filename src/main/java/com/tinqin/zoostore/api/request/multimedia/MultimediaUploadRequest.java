package com.tinqin.zoostore.api.request.multimedia;

import com.tinqin.zoostore.utils.validator.FileNotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class MultimediaUploadRequest {

    @FileNotEmpty
    private MultipartFile file;
}

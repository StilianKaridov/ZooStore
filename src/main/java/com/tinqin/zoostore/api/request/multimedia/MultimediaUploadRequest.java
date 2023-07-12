package com.tinqin.zoostore.api.request.multimedia;

import com.tinqin.zoostore.utils.validator.FileNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaUploadRequest {

    @FileNotEmpty
    private MultipartFile file;
}

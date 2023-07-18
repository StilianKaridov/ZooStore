package com.tinqin.zoostore.api.operations.multimedia.upload;

import com.tinqin.zoostore.api.operations.base.OperationRequest;
import com.tinqin.zoostore.core.validation.FileNotEmpty;
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
public class MultimediaUploadRequest implements OperationRequest {

    @FileNotEmpty
    private MultipartFile file;
}

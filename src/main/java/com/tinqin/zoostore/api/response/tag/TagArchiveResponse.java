package com.tinqin.zoostore.api.response.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagArchiveResponse {

    private UUID id;

    private String title;

    private Boolean isArchived;
}

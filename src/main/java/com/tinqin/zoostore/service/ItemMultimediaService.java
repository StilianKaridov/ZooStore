package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.item.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.response.item.ItemAddMultimediaResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveMultimediaResponse;

public interface ItemMultimediaService {

    ItemAddMultimediaResponse addMultimediaToItem(ItemAddMultimediaRequest itemAddMultimediaRequest);

    ItemRemoveMultimediaResponse removeMultimediaFromItem(ItemRemoveMultimediaRequest itemRemoveMultimediaRequest);
}

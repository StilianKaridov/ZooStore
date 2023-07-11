package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.VendorCreateRequest;
import com.tinqin.zoostore.api.response.VendorCreateResponse;
import com.tinqin.zoostore.api.response.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.VendorUpdatePhoneResponse;

public interface VendorService {

    VendorCreateResponse createVendor(VendorCreateRequest vendorCreateRequest);

    VendorUpdateNameResponse updateVendorName(String vendorName, String vendorNewName);

    VendorUpdatePhoneResponse updateVendorPhone(String vendorName, String vendorNewPhone);

    boolean archiveVendor(String vendorName);

    boolean unarchiveVendor(String vendorName);
}

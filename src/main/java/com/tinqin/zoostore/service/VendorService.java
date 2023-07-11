package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.VendorCreateRequest;
import com.tinqin.zoostore.api.response.VendorArchiveResponse;
import com.tinqin.zoostore.api.response.VendorCreateResponse;
import com.tinqin.zoostore.api.response.VendorDeleteResponse;
import com.tinqin.zoostore.api.response.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.response.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.VendorUpdatePhoneResponse;

public interface VendorService {

    VendorCreateResponse createVendor(VendorCreateRequest vendorCreateRequest);

    VendorUpdateNameResponse updateVendorName(String vendorName, String vendorNewName);

    VendorUpdatePhoneResponse updateVendorPhone(String vendorName, String vendorNewPhone);

    VendorArchiveResponse archiveVendor(String vendorName);

    VendorUnarchiveResponse unarchiveVendor(String vendorName);

    VendorDeleteResponse deleteVendor(String vendorName);
}

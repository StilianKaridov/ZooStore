package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.vendor.VendorCreateRequest;
import com.tinqin.zoostore.api.response.vendor.VendorArchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorCreateResponse;
import com.tinqin.zoostore.api.response.vendor.VendorDeleteResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.data.entity.Vendor;

import java.util.UUID;

public interface VendorService {

    VendorCreateResponse createVendor(VendorCreateRequest vendorCreateRequest);

    VendorUpdateNameResponse updateVendorName(String vendorName, String vendorNewName);

    VendorUpdatePhoneResponse updateVendorPhone(String vendorName, String vendorNewPhone);

    VendorArchiveResponse archiveVendor(String vendorName);

    VendorUnarchiveResponse unarchiveVendor(String vendorName);

    VendorDeleteResponse deleteVendor(String vendorName);

    Vendor getVendorById(UUID id);
}

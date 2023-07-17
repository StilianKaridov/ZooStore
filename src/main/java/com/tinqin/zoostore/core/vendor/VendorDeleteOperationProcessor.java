package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteOperation;
import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteRequest;
import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteResponse;
import com.tinqin.zoostore.api.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.api.exception.vendor.VendorArchivedException;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorDeleteOperationProcessor implements VendorDeleteOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorDeleteOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorDeleteResponse process(VendorDeleteRequest input) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(input.getVendorName())
                .orElseThrow(NoSuchVendorException::new);

        if (vendor.getIsArchived()) {
            throw new VendorArchivedException();
        }

        this.vendorRepository.delete(vendor);

        return this.modelMapper.map(vendor, VendorDeleteResponse.class);
    }
}

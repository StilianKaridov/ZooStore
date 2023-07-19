package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveRequest;
import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveResponse;
import com.tinqin.zoostore.core.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.core.exception.vendor.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveOperation;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorUnarchiveOperationProcessor implements VendorUnarchiveOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorUnarchiveOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorUnarchiveResponse process(VendorUnarchiveRequest input) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(input.getVendorName()).
                orElseThrow(NoSuchVendorException::new);

        if (!vendor.getIsArchived()) {
            throw new VendorAlreadyUnarchivedException();
        }

        Vendor unarchivedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .phoneNumber(vendor.getPhoneNumber())
                .isArchived(Boolean.FALSE)
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(unarchivedVendor);

        return this.modelMapper.map(unarchivedVendor, VendorUnarchiveResponse.class);
    }
}

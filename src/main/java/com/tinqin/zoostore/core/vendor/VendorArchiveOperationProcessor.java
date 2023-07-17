package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveRequest;
import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveResponse;
import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveOperation;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import com.tinqin.zoostore.api.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.api.exception.vendor.VendorAlreadyArchivedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorArchiveOperationProcessor implements VendorArchiveOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorArchiveOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorArchiveResponse process(VendorArchiveRequest input) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(input.getVendorName()).
                orElseThrow(NoSuchVendorException::new);

        if (vendor.getIsArchived()) {
            throw new VendorAlreadyArchivedException();
        }

        Vendor archivedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .phoneNumber(vendor.getPhoneNumber())
                .isArchived(Boolean.TRUE)
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(archivedVendor);

        return this.modelMapper.map(archivedVendor, VendorArchiveResponse.class);
    }
}

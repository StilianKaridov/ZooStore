package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateOperation;
import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateRequest;
import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateResponse;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import com.tinqin.zoostore.api.exception.vendor.VendorAlreadyExistingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorCreateOperationProcessor implements VendorCreateOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorCreateOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorCreateResponse process(VendorCreateRequest input) {
        String vendorName = input.getName();
        String phoneNumber = input.getPhoneNumber();

        Optional<Vendor> vendorOpt = this.vendorRepository.findFirstByNameOrPhoneNumber(vendorName, phoneNumber);

        if (vendorOpt.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor vendor = Vendor
                .builder()
                .name(vendorName)
                .phoneNumber(phoneNumber)
                .isArchived(Boolean.FALSE)
                .build();

        this.vendorRepository.save(vendor);

        return this.modelMapper.map(vendor, VendorCreateResponse.class);
    }
}

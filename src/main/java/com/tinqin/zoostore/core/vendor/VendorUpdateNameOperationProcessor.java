package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameOperation;
import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameRequest;
import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameResponse;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import com.tinqin.zoostore.api.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.api.exception.vendor.VendorAlreadyExistingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorUpdateNameOperationProcessor implements VendorUpdateNameOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorUpdateNameOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorUpdateNameResponse process(VendorUpdateNameRequest input) {
        String newName = input.getNewName();

        Vendor vendor = this.vendorRepository.
                findFirstByName(input.getOldName())
                .orElseThrow(NoSuchVendorException::new);

        Optional<Vendor> checkIfNewNameExist = this.vendorRepository.findFirstByName(newName);

        if (checkIfNewNameExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor updatedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(newName)
                .phoneNumber(vendor.getPhoneNumber())
                .isArchived(vendor.getIsArchived())
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(updatedVendor);

        return this.modelMapper.map(updatedVendor, VendorUpdateNameResponse.class);
    }
}

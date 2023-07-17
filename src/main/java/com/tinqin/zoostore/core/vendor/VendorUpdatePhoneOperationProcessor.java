package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneOperation;
import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneRequest;
import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import com.tinqin.zoostore.api.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.api.exception.vendor.VendorAlreadyExistingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorUpdatePhoneOperationProcessor implements VendorUpdatePhoneOperation {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorUpdatePhoneOperationProcessor(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorUpdatePhoneResponse process(VendorUpdatePhoneRequest input) {
        String newPhone = input.getNewPhone();

        Vendor vendor = this.vendorRepository.
                findFirstByPhoneNumber(input.getOldPhone())
                .orElseThrow(NoSuchVendorException::new);

        Optional<Vendor> checkIfNewPhoneExist = this.vendorRepository.findFirstByPhoneNumber(newPhone);

        if (checkIfNewPhoneExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor updatedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .phoneNumber(newPhone)
                .isArchived(vendor.getIsArchived())
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(updatedVendor);

        return this.modelMapper.map(updatedVendor, VendorUpdatePhoneResponse.class);
    }
}

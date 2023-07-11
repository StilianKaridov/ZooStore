package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.VendorCreateRequest;
import com.tinqin.zoostore.api.response.VendorCreateResponse;
import com.tinqin.zoostore.api.response.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.data.entity.Vendor;
import com.tinqin.zoostore.data.repository.VendorRepository;
import com.tinqin.zoostore.exception.NoSuchVendorException;
import com.tinqin.zoostore.exception.VendorAlreadyExistingException;
import com.tinqin.zoostore.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorCreateResponse createVendor(VendorCreateRequest vendorCreateRequest) {
        String vendorName = vendorCreateRequest.getName();
        String phoneNumber = vendorCreateRequest.getPhoneNumber();

        Optional<Vendor> vendorOpt = this.vendorRepository.findFirstByNameOrPhoneNumber(vendorName, phoneNumber);

        if (vendorOpt.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor vendor = new Vendor();
        vendor.setName(vendorName);
        vendor.setPhoneNumber(phoneNumber);

        this.vendorRepository.save(vendor);

        return this.modelMapper.map(vendor, VendorCreateResponse.class);
    }

    @Override
    public VendorUpdateNameResponse updateVendorName(String vendorName, String vendorNewName) {
        Optional<Vendor> vendorOpt = this.vendorRepository.findFirstByName(vendorName);

        if (vendorOpt.isEmpty()) {
            throw new NoSuchVendorException();
        }

        Optional<Vendor> checkIfNewNameExist = this.vendorRepository.findFirstByName(vendorNewName);

        if (checkIfNewNameExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor vendorEntity = vendorOpt.get();
        vendorEntity.setName(vendorNewName);

        this.vendorRepository.save(vendorEntity);

        return VendorUpdateNameResponse.
                builder().
                oldName(vendorName).
                newName(vendorNewName).
                build();
    }

    @Override
    public VendorUpdatePhoneResponse updateVendorPhone(String vendorPhone, String vendorNewPhone) {
        Optional<Vendor> vendorOpt = this.vendorRepository.findFirstByPhoneNumber(vendorPhone);

        if (vendorOpt.isEmpty()) {
            throw new NoSuchVendorException();
        }

        Optional<Vendor> checkIfNewPhoneExist = this.vendorRepository.findFirstByPhoneNumber(vendorNewPhone);

        if (checkIfNewPhoneExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor vendorEntity = vendorOpt.get();

        String vendorOldPhone = vendorEntity.getPhoneNumber();

        vendorEntity.setPhoneNumber(vendorNewPhone);

        this.vendorRepository.save(vendorEntity);

        return VendorUpdatePhoneResponse.
                builder().
                name(vendorOldPhone).
                oldPhone(vendorOldPhone).
                newPhone(vendorNewPhone).
                build();
    }
}

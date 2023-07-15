package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.vendor.VendorCreateRequest;
import com.tinqin.zoostore.api.response.vendor.VendorArchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorCreateResponse;
import com.tinqin.zoostore.api.response.vendor.VendorDeleteResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.data.entity.Vendor;
import com.tinqin.zoostore.data.repository.VendorRepository;
import com.tinqin.zoostore.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyArchivedException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyExistingException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.exception.vendor.VendorArchivedException;
import com.tinqin.zoostore.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

        Vendor vendor = Vendor
                .builder()
                .name(vendorName)
                .phoneNumber(phoneNumber)
                .isArchived(Boolean.FALSE)
                .build();

        this.vendorRepository.save(vendor);

        return this.modelMapper.map(vendor, VendorCreateResponse.class);
    }

    @Override
    public VendorUpdateNameResponse updateVendorName(String vendorName, String vendorNewName) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(vendorName)
                .orElseThrow(NoSuchVendorException::new);

        Optional<Vendor> checkIfNewNameExist = this.vendorRepository.findFirstByName(vendorNewName);

        if (checkIfNewNameExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor updatedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(vendorNewName)
                .phoneNumber(vendor.getPhoneNumber())
                .isArchived(vendor.getIsArchived())
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(updatedVendor);

        return this.modelMapper.map(updatedVendor, VendorUpdateNameResponse.class);
    }

    @Override
    public VendorUpdatePhoneResponse updateVendorPhone(String vendorPhone, String vendorNewPhone) {
        Vendor vendor = this.vendorRepository.
                findFirstByPhoneNumber(vendorPhone)
                .orElseThrow(NoSuchVendorException::new);

        Optional<Vendor> checkIfNewPhoneExist = this.vendorRepository.findFirstByPhoneNumber(vendorNewPhone);

        if (checkIfNewPhoneExist.isPresent()) {
            throw new VendorAlreadyExistingException();
        }

        Vendor updatedVendor = Vendor
                .builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .phoneNumber(vendorNewPhone)
                .isArchived(vendor.getIsArchived())
                .items(vendor.getItems())
                .build();

        this.vendorRepository.save(updatedVendor);

        return this.modelMapper.map(updatedVendor, VendorUpdatePhoneResponse.class);
    }

    @Override
    public VendorArchiveResponse archiveVendor(String vendorName) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(vendorName).
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

    @Override
    public VendorUnarchiveResponse unarchiveVendor(String vendorName) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(vendorName).
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

    @Override
    public VendorDeleteResponse deleteVendor(String vendorName) {
        Vendor vendor = this.vendorRepository.
                findFirstByName(vendorName)
                .orElseThrow(NoSuchVendorException::new);

        if (vendor.getIsArchived()) {
            throw new VendorArchivedException();
        }

        this.vendorRepository.delete(vendor);

        return this.modelMapper.map(vendor, VendorDeleteResponse.class);
    }

    @Override
    public Vendor getVendorById(UUID id) {
        return this.vendorRepository.findById(id).orElseThrow(NoSuchVendorException::new);
    }
}

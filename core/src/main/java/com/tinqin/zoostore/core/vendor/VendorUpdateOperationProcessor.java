package com.tinqin.zoostore.core.vendor;

import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateOperation;
import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateRequest;
import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateResponse;
import com.tinqin.zoostore.core.exception.vendor.InvalidPhoneNumberFormatException;
import com.tinqin.zoostore.core.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.core.exception.vendor.VendorAlreadyExistingException;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VendorUpdateOperationProcessor implements VendorUpdateOperation {

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorUpdateOperationProcessor(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorUpdateResponse process(VendorUpdateRequest input) {
        UUID id = UUID.fromString(input.getVendorId());

        Vendor vendor = this.vendorRepository.findById(id)
                .orElseThrow(NoSuchVendorException::new);

        Optional<String> nameOpt = input.getName();

        nameOpt.ifPresent(vendorName -> {
            this.vendorRepository.findFirstByName(vendorName).ifPresent(value -> {
                throw new VendorAlreadyExistingException();
            });

            vendor.setName(vendorName);
        });

        Optional<String> phoneNumberOpt = input.getPhoneNumber();

        if (phoneNumberOpt.isPresent()) {
            String phoneNumber = phoneNumberOpt.get();

            Pattern patternWithPlus = Pattern.compile("^\\+3598[789]\\d{7}$");
            Pattern patternWithZero = Pattern.compile("^08[789]\\d{7}$");

            Matcher matcherWithPlus = patternWithPlus.matcher(phoneNumber);
            Matcher matcherWithZero = patternWithZero.matcher(phoneNumber);

            if (!matcherWithPlus.find() && !matcherWithZero.find()) {
                throw new InvalidPhoneNumberFormatException();
            }

            vendor.setPhoneNumber(phoneNumber);
        }

        Vendor updatedVendor = this.vendorRepository.save(vendor);

        return VendorUpdateResponse
                .builder()
                .vendorId(String.valueOf(id))
                .name(updatedVendor.getName())
                .phoneNumber(updatedVendor.getPhoneNumber())
                .build();
    }
}

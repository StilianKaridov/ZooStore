package com.tinqin.zoostore.persistence.repository;

import com.tinqin.zoostore.persistence.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    Optional<Vendor> findFirstByNameOrPhoneNumber(String name, String phoneNumber);

    Optional<Vendor> findFirstByName(String name);

    Optional<Vendor> findFirstByPhoneNumber(String vendorPhone);
}

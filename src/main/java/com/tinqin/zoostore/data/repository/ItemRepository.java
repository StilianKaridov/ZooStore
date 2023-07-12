package com.tinqin.zoostore.data.repository;

import com.tinqin.zoostore.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findFirstByTitle(String title);
}

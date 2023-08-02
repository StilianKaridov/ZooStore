package com.tinqin.zoostore.persistence.repository;

import com.tinqin.zoostore.persistence.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> getItemById(UUID id);

    Optional<Item> findFirstByTitle(String title);

    Page<Item> findAllByTags_Title(String title, Pageable pageable);

    List<Item> findAllByIdIn(List<UUID> ids);

    Page<Item> findAllByTitleContaining(String title, Pageable pageable);
}

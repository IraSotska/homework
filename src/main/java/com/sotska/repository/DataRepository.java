package com.sotska.repository;

import com.sotska.entity.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
    Page<Data> findAll(Pageable pageable);

    @Modifying
    @Query("update Data d set d.modificationDate = :modificationDate where d.id in :ids")
    void updateModificationDate(@Param(value = "ids") List<Long> ids, @Param(value = "modificationDate") LocalDateTime modificationDate);
}

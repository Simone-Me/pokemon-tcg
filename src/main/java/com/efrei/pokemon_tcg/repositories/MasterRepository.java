package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MasterRepository extends JpaRepository<Master, String> {

    Master findMasterByUuid(String uuid);

    List<Master> findAllByDeleteAtNull();

    @Modifying
    @Query(value = "DELETE FROM master_package_cards_primary WHERE package_cards_primary_uuid = :uuid", nativeQuery = true)
    void removeCardAssociations(@Param("uuid") String uuid);

}

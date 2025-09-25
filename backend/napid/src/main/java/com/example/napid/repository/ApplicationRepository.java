package com.example.napid.repository;

import com.example.napid.entity.ApplicationDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationDetailsEntity,Integer> {

    Optional<ApplicationDetailsEntity> findByApplicationName(String applicationName);
    Optional<ApplicationDetailsEntity> findByDigitalID(String digitalID);
}

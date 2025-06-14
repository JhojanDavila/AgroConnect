package com.agroconnect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroconnect.backend.entity.Ubigeo;

@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, String> {
}
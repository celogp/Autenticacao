package com.sw1tech.app.authoritie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritieRepository extends JpaRepository<AuthoritieEntity, Integer> {
    
}
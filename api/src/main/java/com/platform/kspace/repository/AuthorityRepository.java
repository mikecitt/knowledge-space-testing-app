package com.platform.kspace.repository;

import com.platform.kspace.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByName(String name);
}

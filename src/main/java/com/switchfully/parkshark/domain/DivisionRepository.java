package com.switchfully.parkshark.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

}

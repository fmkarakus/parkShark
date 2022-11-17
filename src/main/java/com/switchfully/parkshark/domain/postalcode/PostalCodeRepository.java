package com.switchfully.parkshark.domain.postalcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeRepository extends JpaRepository<PostalCode,String> {
}

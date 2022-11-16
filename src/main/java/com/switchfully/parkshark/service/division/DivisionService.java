package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.domain.division.DivisionRepository;
import com.switchfully.parkshark.service.exceptions.ObjectAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DivisionService {

    DivisionRepository divisionRepository;
    DivisionMapper divisionMapper = new DivisionMapper();
    DivisionValidator divisionValidator = new DivisionValidator();
    private final Logger logger = LoggerFactory.getLogger(DivisionValidator.class);

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public void createDivision(CreateDivisionDTO createDivisionDTO) {
        divisionValidator.CheckRequiredFields(createDivisionDTO);
        if(divisionRepository.existsDivisionByName(createDivisionDTO.getName())) {
            logger.error("Division already exists");
            throw new ObjectAlreadyExistsException("This division name is already in use");
        }
        divisionRepository.save(divisionMapper.toDivision(createDivisionDTO));
    }
}

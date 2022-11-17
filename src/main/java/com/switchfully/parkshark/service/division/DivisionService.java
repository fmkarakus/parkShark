package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.domain.division.DivisionRepository;
import com.switchfully.parkshark.service.division.DTO.CreateDivisionDTO;
import com.switchfully.parkshark.service.division.DTO.DivisionDTO;
import com.switchfully.parkshark.service.division.DTO.SubdivisionDTO;
import com.switchfully.parkshark.service.exceptions.ObjectAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Transactional
public class DivisionService {

    DivisionRepository divisionRepository;
    DivisionMapper divisionMapper = new DivisionMapper();
    DivisionValidator divisionValidator = new DivisionValidator();
    private final Logger logger = LoggerFactory.getLogger(DivisionValidator.class);

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public DivisionDTO createDivision(CreateDivisionDTO createDivisionDTO) {
        divisionValidator.CheckRequiredFields(createDivisionDTO);
        if (divisionRepository.existsDivisionByName(createDivisionDTO.getName())) {
            logger.error("Division already exists");
            throw new ObjectAlreadyExistsException("This division name is already in use");
        }
        Division division= divisionRepository.save(divisionMapper.toDivision(createDivisionDTO));
        return divisionMapper.toDivisionDTO(division);
    }

    public List<DivisionDTO> getAllDivisions() {
        return divisionRepository.findAll().stream()
                .map(division -> divisionMapper.toDivisionDTO(division))
                .collect(Collectors.toList());
    }

    public DivisionDTO getADivisionById(Long divisionId) {
        return divisionMapper.toDivisionDTO(divisionRepository.findById(divisionId).orElseThrow(() -> new NoSuchElementException("Division by id " + divisionId + " was not found")));
    }

    public SubdivisionDTO createSubdivision(Long parentId, CreateDivisionDTO createDivisionDTO) {
        Division parentDivision=divisionRepository.findById(parentId).orElseThrow(()->new IllegalArgumentException("No division found with the id: "+parentId));
        DivisionDTO divisionDTO=createDivision(createDivisionDTO);
        divisionRepository.findById(divisionDTO.id()).ifPresent(division -> division.setParentDivision(parentDivision));
        return divisionMapper.toSubdivisionDTO(divisionRepository.findById(divisionDTO.id()).get());
    }

    public Division findDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId).orElseThrow(()-> new IllegalArgumentException("Invalid division id"));
    }
}

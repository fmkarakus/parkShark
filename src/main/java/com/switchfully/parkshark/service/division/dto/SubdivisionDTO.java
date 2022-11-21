package com.switchfully.parkshark.service.division.dto;

public record SubdivisionDTO(
        Long id,
        String name,
        String originalName,
        String director,
        Long parentId
) {
}

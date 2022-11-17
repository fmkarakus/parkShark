package com.switchfully.parkshark.service.division.DTO;

public record SubdivisionDTO(
        Long id,
        String name,
        String originalName,
        String director,
        Long parentId
) {
}

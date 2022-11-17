package com.switchfully.parkshark.service.division.DTO;

public class CreateDivisionDTO {
    private final String name;
    private final String originalName;
    private final String director;


    public CreateDivisionDTO(String name, String originalName, String director) {
        this.name = name;
        this.originalName = originalName;
        this.director = director;
    }


    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getDirector() {
        return director;
    }
}

package com.switchfully.parkshark.service.division;

public class CreateDivisionDTO {
    private String name;
    private String originalName;
    private String director;



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

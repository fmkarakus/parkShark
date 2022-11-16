package com.switchfully.parkshark.domain.Division;


import javax.persistence.*;

@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_seq")
    @SequenceGenerator(name = "division_seq", sequenceName = "division_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "director")
    private String director;

    public Division() {
    }

    public Division(String name, String originalName, String director) {
        this.name = name;
        this.originalName = originalName;
        this.director = director;
    }
}

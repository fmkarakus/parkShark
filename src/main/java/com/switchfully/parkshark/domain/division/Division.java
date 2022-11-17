package com.switchfully.parkshark.domain.division;


import javax.persistence.*;

@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_seq")
    @SequenceGenerator(name = "division_seq", sequenceName = "division_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "director")
    private String director;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Division parentDivision;
    public Division() {
    }

    public Division(String name, String originalName, String director) {
        this.name = name;
        this.originalName = originalName;
        this.director = director;
    }

    public Long getId() {
        return id;
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

    public Division getParentDivision() {
        return parentDivision;
    }

    public void setParentDivision(Division parentDivision) {
        this.parentDivision = parentDivision;
    }
}

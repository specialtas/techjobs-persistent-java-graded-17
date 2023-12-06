package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @OneToMany(mappedBy = "employer")
    @JoinColumn(name = "employer_id") // this specifies the column name in the Job table
    private final List<Job> jobs = new ArrayList<>(); //not sure if this needs to be final

    @NotNull(message = "Location is required")
    @Size(max = 100, message = "Location must be less than 100 characters")
    private String location;


    public Employer(){
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

//    public void setJobs(List<Job> jobs){
//        this.jobs = jobs;
//    }
}

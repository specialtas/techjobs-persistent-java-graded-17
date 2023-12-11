package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity {

   @ManyToMany
   private List<Skill> skills;// not sure about this, is how Carrie did it

//    @ManyToMany
//    private List<Skill> skills = new ArrayList<>();

   @ManyToOne
    private Employer employer;
   public Job() {
    }

    // Initialize the id and value fields.
    public Job(Employer employer, List<Skill> skills) {
        super();
        this.employer = employer;
        this.skills = skills;
    }

    // Getters and setters.



    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
       return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;}
//    } not sure this is how they want it
        public void addSkills(Skill skill){
        this.skills.add(skill);
        }

}

package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll()); //this passes employer data to the form
        model.addAttribute("skills", skillRepository.findAll()); //this passes the skill data to the form
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam Integer employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Optional<Employer> result = employerRepository.findById(employerId);

        if (result.isPresent()) {
            Employer selectedEmployer = result.get();
            newJob.setEmployer(selectedEmployer);// this sets the selected employer for the new job

            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillObjs);

            Optional<List<Skill>> skillsResult = Optional.ofNullable(skillObjs);

            if (skillsResult.isPresent()) {
                newJob.setSkills(skillsResult.get());
            } else {
                // Handle the case where skills are null or empty
                // This could involve displaying an error message or taking appropriate action
                model.addAttribute("title", "No Skills Found");
                return "add";
            }

            return "redirect:/"; // Redirect to the home page or another appropriate route
        } else {
            model.addAttribute("title", "Invalid Employer ID: " + employerId);
            return "add";
        }
    }
}
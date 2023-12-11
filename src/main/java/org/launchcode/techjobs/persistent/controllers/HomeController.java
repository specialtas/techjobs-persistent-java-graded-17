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
                                    Errors errors, Model model,
                                    @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Optional<Employer> employerResult = employerRepository.findById(employerId);
        if (employerResult.isEmpty()) {
            model.addAttribute("title", "Invalid Employer ID: " + employerId);
            return "add";
        }

        Employer selectedEmployer = employerResult.get();
        newJob.setEmployer(selectedEmployer);


        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        newJob.setSkills(skillObjs);
        if (skillObjs == null || skillObjs.isEmpty()) {
            model.addAttribute("title", "No Skills Found");
            return "add";
        }

//        Iterable<Integer> skillIds = skills; // Assuming skills is already an Iterable
//        skillRepository.findAllById(skillIds);
        jobRepository.save(newJob);

        return "redirect:/"; // Redirect to the home page or another appropriate route
    }
}
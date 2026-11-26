package me.tuya.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleaf(Model model){
        Person person = new Person();
        person.setName("Ari");
        person.setAge(25);
        person.setHobbies(List.of("coding","painting","running"));
        model.addAttribute("student", person);
        return "example";
    }
    @GetMapping("/thymeleaf/test")
    public String test(Model model){
        model.addAttribute("name","tuya");
        model.addAttribute("age", "23");
        List<String> hobbies = new ArrayList<>();
        hobbies.add("exercising");
        hobbies.add("reading");
        hobbies.add("running");
        model.addAttribute("hobbies", hobbies);
        return "test";
    }

    @GetMapping("/thymeleaf/test2")
    public String test2(Model model){
        Person person = new Person();
        person.setName("tuya");
        person.setAge(23);
        List<String> hobbies = new ArrayList<>();
        hobbies.add("exercising");
        hobbies.add("reading");
        hobbies.add("running");
        person.setHobbies(hobbies);
        model.addAttribute("person", person);
        model.addAttribute("blogId", 2);
        model.addAttribute("today", LocalDate.now());

        return "test2";
    }
}
@Setter
@Getter
class Person {
    private String name;
    private int age;
    private List<String> hobbies;
}
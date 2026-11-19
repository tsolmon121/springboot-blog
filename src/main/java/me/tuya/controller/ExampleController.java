package me.tuya.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
@Setter
@Getter
class Person {
    private String name;
    private int age;
    private List<String> hobbies;
}
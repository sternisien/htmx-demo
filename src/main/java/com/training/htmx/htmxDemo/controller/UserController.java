package com.training.htmx.htmxDemo.controller;


import com.training.htmx.htmxDemo.entity.User;
import com.training.htmx.htmxDemo.model.UserDto;
import com.training.htmx.htmxDemo.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    ModelAndView getUsers() {
        List<User> myUser = userRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("users", myUser);
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }


    /**
     *
     * @param userDto le type d'objet traité en entrée
     * @param model le model associé à la vue en retour
     * @return le fragment de la vue  alimenté par les éléments injectés dans le model (addAttribute)
     */
    @PostMapping(value = "/add", produces = MediaType.TEXT_HTML_VALUE)
    public String  addUser(@ModelAttribute UserDto userDto, Model model){
        User user = new User();
        user.setNom(userDto.getNom());
        user.setPrenom(userDto.getPrenom());
        userRepository.saveAndFlush(user);
         model.addAttribute("users",userRepository.findAll());
         model.addAttribute("userDto", new UserDto());
        //TODO : tester si il est possible de faire la meme chose avec le ModelAndView
        return "users :: form-and-user-list";
    }

    @DeleteMapping(value = "/{id}")
    String deleteUser(@PathVariable long id, Model model){
        ModelAndView modelAndView = new ModelAndView();
        userRepository.deleteById(id);
        modelAndView.setViewName("users");
        model.addAttribute("users",userRepository.findAll());
        return "users :: user-list";
    }
}

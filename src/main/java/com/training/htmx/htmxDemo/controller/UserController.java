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
    String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userDto", new UserDto());
        return "users";
    }

    @GetMapping("/{id}/edit")
    String getUserEdit(@PathVariable long id, Model model){
      model.addAttribute("userEdit",  userRepository.findById(id).orElseThrow());
      return "userFormEdit";
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
        return "users :: form-and-user-list";
    }


    /**
     * Permet d'effectuer la maj d'un utilisateur.
     * Fournit seulement l'utilisateur màj à la collection de d'utilisateur coté template html
     * afin de générer le fragment html (row) pour cette utilisateur, c'est à dire la ligne du tableau. Pour
     * remplacer la ligne avec les anciennes valeurs par la nouvelle
     * @param id
     * @param userEdit
     * @param model
     * @return
     */
    @PutMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String  updateUser(@PathVariable long id, @ModelAttribute UserDto userEdit, Model model){
        User userMaj = userRepository.findById(id).orElseThrow();
        userMaj.setNom(userEdit.getNom());
        userMaj.setPrenom(userEdit.getPrenom());
        userRepository.saveAndFlush(userMaj);
        model.addAttribute("users",userMaj);
        return  "users ::  row";
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

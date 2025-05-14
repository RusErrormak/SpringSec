package org.ruserrormak.spring_sec.controller;

import org.ruserrormak.spring_sec.model.Users;
import org.ruserrormak.spring_sec.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UsersService userService;

    @Autowired
    public AdminController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/showUsers";
    }

    @GetMapping("/deleteUser")
    public String showDeleteMessage(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("id", id);
        return "admin/deleteUser";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        Users user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin/updateUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute Users user, @RequestParam(value = "selectedRoles", required = false) String selectedRoles) {
        String[] roles = selectedRoles != null ? selectedRoles.split(",") : new String[0];
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/addUser")
    public String showAddForm(ModelMap model) {
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @PostMapping("/newUser")
    public String createUser(@ModelAttribute Users user, @RequestParam(value = "selectedRoles", required = false) String selectedRoles) {
        String[] roles = selectedRoles != null ? selectedRoles.split(",") : new String[0];
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

}

package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public AdminController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping
  public String getAllUser(Model model) {
    model.addAttribute("user", userService.listUsers());
    return "admin/admin";
  }

  @GetMapping("/new")
  public String addNewUser(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("role", roleService.getAll());
    return "admin/new";
  }

  @PostMapping()
  public String saveNewUser(@ModelAttribute("user") User user) {
    userService.addUser(user);
    return "redirect:/admin";
  }

  @GetMapping("/edit")
  public String edit(@RequestParam(value = "id") int id, Model model) {
    model.addAttribute("user", userService.showUser(id));
    model.addAttribute("role", roleService.getAll());
    return "admin/edit";
  }

  @PostMapping("/edit")
  public String update(@ModelAttribute("user") User user, @RequestParam(value = "id") int id) {
    userService.updateUser(id, user);
    return "redirect:/admin";
  }

  @PostMapping("/delete")
  public String deleteUser(@RequestParam(value = "id") int id) {
    userService.deleteUser(id);
    return "redirect:/admin";
  }
}



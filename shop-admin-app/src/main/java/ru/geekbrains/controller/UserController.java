package ru.geekbrains.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.service.RoleService;
import ru.geekbrains.persist.service.UserService;
import ru.geekbrains.persist.service.dto.UserDto;


import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RoleService roleService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    public String listPage(Model model,
                           @RequestParam("loginFilter") Optional<String> loginFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField,
                           @RequestParam("sortDir") Optional<Sort.Direction> sortDir) {
        String sortBy;
        if (sortField.isPresent() && !sortField.get().isEmpty()) {
            sortBy = sortField.get();
        } else {
            sortBy = "id";
        }


        model.addAttribute(
                "users",
                userService.findAll(
                        loginFilter,
                        page.orElse(1) - 1,
                        size.orElse(10),
                        sortBy,
                        sortDir.orElse(Sort.Direction.ASC)
                ));
        return "user";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        return "user_form";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            logger.error(result.toString());
            return "user_form";
        }
        userService.save(user);
        return "redirect:/user";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}

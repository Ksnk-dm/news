package com.newproject.news.controllers;


import com.newproject.news.entity.Role;
import com.newproject.news.entity.User;
import com.newproject.news.services.RoleServise;
import com.newproject.news.services.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminPageController {
    @Autowired
    private UserServise userServise;
    @Autowired
    private RoleServise roleServise;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/adminpage")
    public String viewAdminPage(Model model) {

        model.addAttribute("users", userServise.allUsers());
        model.addAttribute("role", roleServise.findAll());
        return "adminpage/admin_page";
    }


    @GetMapping("/user/{userName}")
    public String viewUserPage(@PathVariable(value = "userName") String username, Model model) {
        model.addAttribute("role", roleServise.findAll());
        model.addAttribute("user", userServise.findUserByUsername(username));
        return "adminpage/user_page";
    }


    @PostMapping("/adminpage/deluser")
    public ResponseEntity<?> delUser(@RequestParam(value = "toDelete[]", required = false)
                                             long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            userServise.deleteUser(toDelete);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/user/{userName}/addrol")
    @PreAuthorize("#username == authentication.principal.username or hasRole('ROLE_ADMIN')")
    public String addRolToUserRole(@PathVariable(value = "userName") String username, @RequestParam(value = "roles") long rolesId, Model model) {
        User user = userServise.findUserByUsername(username);
        Role role = roleServise.findRole(rolesId);
        userServise.addRoleToUser(user, role);
        return "redirect:/user/{userName}";
    }

    @PostMapping("/user/{userName}/delrole")
    @PreAuthorize("#usernameDel == authentication.principal.username or hasRole('ROLE_ADMIN')")
    public String delUserRole(@PathVariable(value = "userName") String usernameDel, @RequestParam(value = "rolesName") long rolesId) {
        User user = userServise.findUserByUsername(usernameDel);
        Role role = roleServise.findRole(rolesId);
        userServise.delRoleToUser(user, role);
        return "redirect:/user/{userName}";
    }

    @PostMapping("/adminpage/addrole") //???????????????? ???????? ?????????? ???????????????? ????????
    public ResponseEntity<?> addRole() {
        Role role = new Role("ROLE_MODERATOR");
        roleServise.addRole(role);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/adminpage/delrole")
    public ResponseEntity<?> delRole(@RequestParam(value = "toDeleteRole[]", required = false)
                                             long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            roleServise.delRole(toDelete);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/user/{userName}/changepass")
    public String editPassword(@PathVariable(value = "userName") String userName, @RequestParam String pass, Model model) {
        User user = userServise.findUserByUsername(userName);
        userServise.updatePassword(user, pass);
        model.addAttribute("pass", "???????????? ???? ??????????????????");
        return "redirect:/user/{userName}";

    }

    @PostMapping("/user/{userName}/changeemail")
    public ResponseEntity<?> editEmail(@PathVariable(value = "userName") String userName, @RequestParam String email, Model model) {
        User user = userServise.findUserByUsername(userName);
        if (!userServise.updateEmail(user, email)) {
            return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/user/{id}/changeSend")
    public ResponseEntity<?> editEmailSend(@PathVariable(value = "id") Long id, @RequestParam("radio") Boolean radio) {
        User user = userServise.findUserById(id);
        user.setSendEmail(radio);
        userServise.saveUser(user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/user/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable(value = "id") Long id, @RequestParam("block") Boolean radio) {
        User user = userServise.findUserById(id);
        userServise.saveUser(user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

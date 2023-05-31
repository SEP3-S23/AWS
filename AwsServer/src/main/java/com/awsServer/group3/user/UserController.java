package com.awsServer.group3.user;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/report/{user}")
    public ResponseEntity<String> reportUser(@PathVariable("user") String userName){
        userService.reportUser(userName);
        return ResponseEntity.ok("REPORTED");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ban/{user}")
    public ResponseEntity<String> banUser(@PathVariable("user") String userName) {
        userService.banUser(userName);
        return ResponseEntity.ok("BANNED");
    }

}

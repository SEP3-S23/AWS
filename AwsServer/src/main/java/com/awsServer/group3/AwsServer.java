package com.awsServer.group3;

import com.awsServer.group3.user.Role;
import com.awsServer.group3.user.Status;
import com.awsServer.group3.user.User;
import com.awsServer.group3.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AwsServer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AwsServer.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        userRepository.save(
                User.builder()
                        .id(1)
                        .fullName("The boss")
                        .email("the@boss.com")
                        .userName("theboss")
                        .password(passwordEncoder.encode("password"))
                        .birthDate("29/06/2024")
                        .role(Role.ADMIN)
                        .status(Status.ACTIVE)
                        .build()
        );
    }

}

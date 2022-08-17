package com.mysite.SBBPractice2.service;

import com.mysite.SBBPractice2.domain.SiteUser;
import com.mysite.SBBPractice2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/mysite/SBBPractice2/security/userID.txt"))) {
            int id = Integer.parseInt(reader.readLine()) + 1;
            user.setId(id);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/mysite/SBBPractice2/security/userID.txt"))) {
                writer.write(Integer.toString(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}

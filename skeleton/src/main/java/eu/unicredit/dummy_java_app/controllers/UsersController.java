package eu.unicredit.dummy_java_app.controllers;

import eu.unicredit.dummy_java_app.pojos.User;
import eu.unicredit.dummy_java_app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("users")
@Slf4j
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "create", method = POST)
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info("Inserting user {}", user);
        final User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, CREATED);
    }

    @RequestMapping(value = "read", method = GET)
    public ResponseEntity<List<User>> read() {
        log.info("Reading users");
        List<User> userList = (List<User>) userRepository.findAll();
        return new ResponseEntity<>(userList, OK);
    }

    @RequestMapping(value = "update/{id}", method = POST)
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        log.info("Reading user with id {}", id);
        final User userOptional = userRepository.findOne(id);

        if (userOptional == null) {
            log.info("User with id {} NOT FOUND", id);
            return new ResponseEntity<>(null, NOT_FOUND);
        }

        //final User userToUpdate = userOptional.get();
        userOptional.setFirstName(user.getFirstName());
        userOptional.setLastName(user.getLastName());

        log.info("Updating user {}", user);
        final User updatedUser = userRepository.save(userOptional);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @RequestMapping(value = "delete/{id}", method = POST)
    public HttpStatus delete(@PathVariable Long id) {
        log.info("Delete user with id {}", id);
        userRepository.delete(id);
        return OK;
    }
}

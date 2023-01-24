package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    // create, get, update, delete
    private GetUser getUser;
    private CreateUser createUser;
    private UpdateUser updateUser;
    private DeleteUser deleteUser;
    private UserRepository userRepository;


    public UserRestController(
            GetUser getUser,
            CreateUser createUser,
            UpdateUser updateUser,
            DeleteUser deleteUser,
            UserRepository userRepository
    ) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public List<User> get (){
        return getUser.getAll();
    }

    @PostMapping()
    ResponseEntity<User> post(@RequestBody User newUser) {
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id){
        deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.update(newUser, id), HttpStatus.OK);
    }

    @GetMapping("/pages")
    List<User> getUsersPages(@RequestParam int page,@RequestParam int size) {
        return getUser.getUsersPage(PageRequest.of(page, size));
    }
}

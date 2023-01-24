package com.fundamentosplatzi.springboot.fundamentos.caseuse;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface GetUser {
    List<User> getAll();
    List<User> getUsersPage(PageRequest pageRequest);
}

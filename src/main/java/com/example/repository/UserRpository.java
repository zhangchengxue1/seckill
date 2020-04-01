package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by think on 2020/3/31.
 */
@Repository
public interface UserRpository extends JpaRepository<User,String> {

    public User findByUsernameAndPassword(String username,String Password);

    public User findByUsername(String username);


}

package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT)
        {
            throw new Exception("Usuario lojista não esta autorizado a fazer a transação");
        }
        if(sender.getBalance().compareTo(amount)<0)
        {
            throw new Exception(" Usuario não tem saldo suficiente");
        }
    }
    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(()->new Exception("Usuario não encontrado"));
    }
    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public void saveUser (User user) {
        this.repository.save(user);
    }
    public List<User> getAllUsers()
    {
       return this.repository.findAll();

    }
}

package com.picpaysimplificado.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public void validateTransaction(User sender, BigDecimal amount) throws Exception {
		
		if (sender.getUserType() == UserType.MERCHANT) {
			throw new Exception("Usuário do tipo logista não está autorizado a realizar transação");
		}
		
		if (sender.getBalance().compareTo(amount) < 0) {
			throw new Exception("Saldo Insuficiente");
		}
		
	}
	
	
	public User findUserById(Long id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado!"));
	}
	
	public void saveUser(User user) {
		this.repository.save(user);
	}
	
	public User createUser(UserDTO data) {
		User newUser = new User(data);
		this.saveUser(newUser);
		return newUser;
		
	}


	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return this.repository.findAll();
	}

}

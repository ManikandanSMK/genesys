package com.genesys.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesys.entity.User;
import com.genesys.repository.UserRepository;
import com.genesys.utils.EntityBuilder;
import com.genesys.utils.Utils;

@Service
public class UserManagementService {

	@Autowired
	private UserRepository userRepo;

	public User saveUser(com.genesys.bo.User user) throws Exception {
		User userEty = new User();
		long count = userRepo.countByEmail(user.getEmail());
		if (count != 0) {
			return null;
		}
		EntityBuilder.buildEntity(user, userEty);
		return userRepo.save(userEty);
	}

	public User updateUser(com.genesys.bo.User user) throws Exception {
		User userEty = userRepo.findByEmail(user.getEmail());
		if (userEty == null) {
			return null;
		}
		EntityBuilder.buildEntity(user, userEty);
		return userRepo.save(userEty);
	}

	public long deleteUser(String email) {
		return userRepo.deleteByEmail(email);
	}

	public List<com.genesys.bo.User> listAllUsers() {
		List<User> userList = userRepo.findAll();
		List<com.genesys.bo.User> userListBo = EntityBuilder.copy(userList);
		return userListBo;
	}

	public List<com.genesys.bo.User> listUser(String email) {
		if(email==null||email.isEmpty()){
			return listAllUsers();
		}
		User userEty = userRepo.findByEmail(email);
		if(userEty!=null){
			List<User> userList=Arrays.asList(userEty);
			return EntityBuilder.copy(userList);
		}	
		return null;
	}
	public User login(com.genesys.bo.User user)throws Exception{
		User userEty=userRepo.findByEmail(user.getEmail());
		if(userEty==null){
			return null;
		}
		if(!userEty.getPassword().equals(user.getPassword())){
			return null;
		}
		userEty.setDate(Utils.getDateFormat().parse(user.getDateTime()));
		return userRepo.save(userEty);	
	}

}

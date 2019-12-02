package com.genesys.utils;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.genesys.entity.User;

public class EntityBuilder {

	public static void buildEntity(com.genesys.bo.User user, User userEty) throws Exception {
		copyNonNullValues(userEty, user);

	}

	private static void copyNonNullValues(User userEty, com.genesys.bo.User userBo) throws Exception {
		if (userBo.getAddress() != null && !userBo.getAddress().isEmpty()) {
			userEty.setAddress(userBo.getAddress());
		}
		if (userBo.getEmail() != null && !userBo.getEmail().isEmpty()) {
			userEty.setEmail(userBo.getEmail());
		}
		if (userBo.getName() != null && !userBo.getName().isEmpty()) {
			userEty.setName(userBo.getName());
		}
		if (userBo.getPassword() != null && !userBo.getPassword().isEmpty()) {
			userEty.setPassword(userBo.getPassword());
		}
		if (userBo.getDateTime() != null &&!userBo.getDateTime().isEmpty()) {
			userEty.setDate(Utils.getDateFormat().parse(userBo.getDateTime()));
		}
	}

	public static List<com.genesys.bo.User> copy(List<User> userList) {
		List<com.genesys.bo.User> userBoList = userList.stream()
				.map(e -> new com.genesys.bo.User(e.getName(), e.getAddress(), e.getEmail()))
				.collect(Collectors.toList());
		return userBoList;
	}

	public static com.genesys.bo.User copy(User userEty) {
		return new com.genesys.bo.User(userEty.getName(), userEty.getAddress(), userEty.getEmail());

	}
}

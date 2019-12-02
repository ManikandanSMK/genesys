package com.genesys.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.validation.Valid;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.genesys.bo.Response;
import com.genesys.bo.User;
import com.genesys.repository.UserRepository;
import com.genesys.service.UserManagementService;
import com.genesys.utils.Utils;

@RestController
public class RequestHandler {

	@Autowired
	private UserManagementService userManagermentService;

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<Response> createUser(@RequestBody @Valid User user) throws Exception {
		SimpleDateFormat formatter = Utils.getDateFormat();
		String currentDate = formatter.format(new Date());
		user.setDateTime(currentDate);
		System.out.println("User===" + user);
		com.genesys.entity.User userEty = userManagermentService.saveUser(user);
		Response respones = new Response();
		String msg = "user has been created";
		HttpStatus status = HttpStatus.CREATED;
		if (userEty == null) {
			msg = "user has been already exits";
			status = HttpStatus.CONFLICT;
		}
		respones.setMsg(msg);
		return ResponseEntity.status(status).body(respones);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateUser(@RequestBody @Valid User user) throws Exception {
		Response response = new Response();
		com.genesys.entity.User userEty = userManagermentService.updateUser(user);
		String msg = "user data has been updated";
		HttpStatus status = HttpStatus.ACCEPTED;
		if (userEty == null) {
			msg = "user does not exist";
			status = HttpStatus.OK;
		}
		response.setMsg(msg);
		return ResponseEntity.status(status).body(response);
	}

	@DeleteMapping("/delete/{email}")
	public ResponseEntity<Response> deleteUser(@PathVariable("email") String email) {
		Response response = new Response();
		long count = userManagermentService.deleteUser(email);
		String msg = "user data has been deleted";
		HttpStatus status = HttpStatus.ACCEPTED;
		if (count == 0) {
			msg = "user data does not exist";
			status = HttpStatus.OK;
		}
		response.setMsg(msg);
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping(value = { "/list/{email}", "/list" })
	public ResponseEntity<Response> viewuser(@PathVariable(required = false, name = "email", value = "") String email) {
		Response response = new Response();
		List<User> userBoList = userManagermentService.listUser(email);
		String msg = null;
		HttpStatus status = HttpStatus.OK;
		if (userBoList == null) {
			msg = "no content";
			status = HttpStatus.NO_CONTENT;
		}
		response.setMsg(msg);
		response.setUserList(userBoList);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Response> login(@RequestBody @Valid User user) throws Exception {
		user.setDateTime(Utils.getDateFormat().format(new Date()));
		Response response = new Response();
		com.genesys.entity.User userEty = userManagermentService.login(user);
		String msg = "";
		if (userEty==null) {
			msg = "invalid username or password";
		}else{
			msg="Hai "+userEty.getName()+" login successful";
		}
		response.setMsg(msg);
		return ResponseEntity.ok(response);
	}
}

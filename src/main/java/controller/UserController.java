package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exception.UserNotFoundException;
import model.User;
import repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/user")
	public User newuser(@RequestBody User newuser) {
		return repository.save(newuser);
	}
	
	@GetMapping("/users")
	public List<User> getAllusers(){
		return repository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(()-> new UserNotFoundException(id));
	}
	
	@PutMapping("/user/{id}")
	public User updateUser(@RequestBody User newuser,@PathVariable Long id) {
		return repository.findById(id)
				.map(
						user -> {
							user.setUsername(newuser.getUsername());
							user.setName(newuser.getName());
							user.setEmail(newuser.getEmail());
							
							return repository.save(user);
						}
						).orElseThrow(()-> new UserNotFoundException(id));
	}
	
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable Long id) {
		if(!repository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		repository.deleteById(id);
		return "User with id "+id+" has been deleted successfully";
	}
	

}

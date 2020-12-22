package com.sw1tech.app.user;

import java.util.List;

import com.sw1tech.app.exceptions.ResourceFoundException;
import com.sw1tech.app.exceptions.ResourceNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

	private UserRepository _userRepository;
	private BCryptPasswordEncoder _bCryptPasswordEncoder;

	public UserDetailService(UserRepository userRepository, 
							BCryptPasswordEncoder bCryptPasswordEncoder) {
		this._userRepository = userRepository;
		this._bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity _user = _userRepository.findByUsername(username);
		if (_user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(_user.getUsername(), _user.getPassword(), _user.getAuthorities());
	}

	public void saveUser(UserEntity user){
		UserEntity _user = _userRepository.findByUsername(user.getUsername());
		if (_user != null){
			throw new ResourceFoundException(user.getUsername());
		}
		user.setPassword(_bCryptPasswordEncoder.encode(user.getPassword()));
		_userRepository.save(user);
	}

	public List<UserEntity> loadAllUser(){
		return  _userRepository.findAll();
	}

	public void deleteUserName(String name) throws ResourceNotFoundException{
		UserEntity _user = _userRepository.findByUsername(name);
		if (_user == null){
			throw new ResourceNotFoundException("User not found !.");
		}
		_userRepository.deleteById(_user.getId());
	}

}
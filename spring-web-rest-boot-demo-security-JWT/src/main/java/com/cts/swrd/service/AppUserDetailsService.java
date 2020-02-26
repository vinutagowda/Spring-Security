package com.cts.swrd.service;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.swrd.dao.AppUserEntityRespository;
import com.cts.swrd.model.AppUserEntity;
import com.cts.swrd.model.AppUserModel;



@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserEntityRespository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUserEntity user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(),
				new ArrayList<>());
	}
	
	public AppUserEntity save(AppUserModel userDTO) {
		AppUserEntity user = new AppUserEntity();
		user.setUsername(userDTO.getUsername());
		user.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
		return userDao.save(user);
	}

}
package com.sw1tech.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	private final UserDetailService _userDetailService;

	public UserController(final UserDetailService userDetailService) {
		this._userDetailService = userDetailService;
	}

	@PostMapping("/sign-up")
	public void doSignUp(@Validated @RequestBody final UserEntity _user) {
		_userDetailService.saveUser(_user);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public void doDeleteUser(@RequestParam(name = "name") final String name) {
		_userDetailService.deleteUserName(name);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponse>> doAll() {
		List<UserEntity> _user = _userDetailService.loadAllUser();

		List<UserResponse> _userRes = new ArrayList<UserResponse>();
		_user.stream()
		.forEach( (a) -> {
			_userRes.add(new UserResponse( a.getUsername(), a.isEnabled(), a.isCredentialsNonExpired()));
		});

		return ResponseEntity.ok(_userRes);
	}

	@GetMapping("/loadName")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserResponse> doloadUserName(@RequestParam(name = "name") final String name) {
		final UserDetails userRet = _userDetailService.loadUserByUsername(name);
		if (!userRet.isEnabled()) {
			ResponseEntity.badRequest();
		}
		final UserResponse userRes = new UserResponse(userRet.getUsername(), userRet.isEnabled(),
				userRet.isAccountNonExpired());
		return ResponseEntity.ok(userRes);
	}

}

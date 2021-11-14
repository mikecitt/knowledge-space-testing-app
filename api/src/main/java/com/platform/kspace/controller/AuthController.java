package com.platform.kspace.controller;

import com.platform.kspace.dto.StudentDTO;
import com.platform.kspace.dto.UserLoginDTO;
import com.platform.kspace.dto.UserTokenStateDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.model.User;
import com.platform.kspace.securingweb.auth.TokenUtils;
import com.platform.kspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(
            @RequestBody UserLoginDTO authenticationRequest
    ) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDTO registrationForm) {
        try {
            userService.registerStudent(registrationForm);
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

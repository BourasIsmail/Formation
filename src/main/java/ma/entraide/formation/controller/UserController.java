package ma.entraide.formation.controller;

import ma.entraide.formation.entity.AuthRequest;
import ma.entraide.formation.entity.UserInfo;
import ma.entraide.formation.service.JwtService;
import ma.entraide.formation.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security tutorials !!";
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
        try {
            String result = userInfoService.addUser(userInfo);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if(authenticate.isAuthenticated()){
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getEmail()));
            }else {
                throw new UsernameNotFoundException("Invalid user request");
            }
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        }

    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        List<UserInfo> users = userInfoService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<?> getAllUsers(@PathVariable Integer id) {
        try {
            UserInfo user = userInfoService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id){
        try {
            userInfoService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!.");
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody UserInfo userInfo){
        try {
            userInfoService.updateUser(id, userInfo);
            return ResponseEntity.ok("User updated successfully!.");
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

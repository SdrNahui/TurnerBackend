package com.example.turner.Controller;

import com.example.turner.DTO.ChangePasswordRequest;
import com.example.turner.DTO.LoginRequest;
import com.example.turner.DTO.LoginResponse;
import com.example.turner.Model.Role;
import com.example.turner.Model.User;
import com.example.turner.Repository.UserRepository;
import com.example.turner.Security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/turner/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtService jwtService,
                          PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        User u1 = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if(!passwordEncoder.matches(request.getPassword(), u1.getPassword())){
            return ResponseEntity.status(401).build();
        }
        String token = jwtService.generateToken(u1.getUsername(), u1.getRole().name());
        return ResponseEntity.ok(new LoginResponse(token, u1.getRole().name()));
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request,
                                                 @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña actual incorrecta");
        }

        user.setUsername(request.getNewUsername() != null && !request.getNewUsername().isBlank()
                ? request.getNewUsername() : user.getUsername());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Actualizado");
    }
    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        User user = userRepository.findByUsername("Claudio")
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        user.setPassword(passwordEncoder.encode("nueva123"));
        userRepository.save(user);
        return ResponseEntity.ok("Reseteado");
    }
}

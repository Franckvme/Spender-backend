package Spender.Spender_backend.module.iam.controller;

import Spender.Spender_backend.module.iam.application.UserService;
import Spender.Spender_backend.module.iam.dto.SignUpRequest;
import Spender.Spender_backend.module.iam.dto.SignUpResponse;
import Spender.Spender_backend.shared.dto.response.ResponseEntityFactory;
import Spender.Spender_backend.shared.dto.response.SingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/iam/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<SingleResponse<SignUpResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntityFactory.buildSingleResponse(HttpStatus.CREATED, userService.signup(signUpRequest));
  }
}

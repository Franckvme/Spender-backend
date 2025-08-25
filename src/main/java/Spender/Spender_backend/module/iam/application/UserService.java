package Spender.Spender_backend.module.iam.application;

import Spender.Spender_backend.module.iam.dto.SignUpRequest;
import Spender.Spender_backend.module.iam.dto.SignUpResponse;
import Spender.Spender_backend.module.iam.usecase.user.SignUp;
import Spender.Spender_backend.shared.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class UserService {

  private final SignUp signUp;

  public SignUpResponse signup(SignUpRequest signUpRequest){
    return signUp.execute(signUpRequest);
  }
}

package Spender.Spender_backend.module.iam.usecase.user;

import Spender.Spender_backend.module.iam.data.datastore.UserDataStore;
import Spender.Spender_backend.module.iam.dto.SignUpRequest;
import Spender.Spender_backend.module.iam.dto.SignUpResponse;
import Spender.Spender_backend.module.iam.security.PasswordEncryptor;
import Spender.Spender_backend.shared.annotation.UseCaseCommand;
import Spender.Spender_backend.shared.generator.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCaseCommand
public class SignUp {

  private final UserDataStore userDataStore;
  private final IdGenerator idGenerator;
  private final PasswordEncryptor passwordEncryptor;

  public SignUpResponse execute(SignUpRequest request) {
    request.validate();

    userDataStore.requireNotExistsByUsername(request.username());

    userDataStore.requireNotExistsByContact(
      request.getContactValue(),
      request.getContactType()
    );

    var user = request.toUser(
      idGenerator.next(),
      passwordEncryptor.encrypt(request.password())
    );

    var createdUser = userDataStore.signUp(user);

    return SignUpResponse.fromUser(createdUser);
  }
}

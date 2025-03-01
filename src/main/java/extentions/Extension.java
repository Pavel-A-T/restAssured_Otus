package extentions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dto.StoreDTO;
import dto.SuccessResponseDTO;
import dto.UserDTO;
import guice.GuiceModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import services.StoreApi;
import services.UserApi;
import specifications.Specification;

public class Extension implements BeforeEachCallback, AfterAllCallback {
  private static Injector injector;

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    injector = Guice.createInjector(new GuiceModule());
    injector.injectMembers(extensionContext.getTestInstance().get());
    Specification.installSpecification(Specification.requestSpecification(System.getProperty("base.url")), Specification.responseSpecification(200));
  }

  @Override
  public void afterAll(ExtensionContext extensionContext) {
    extensionContext.getTestInstance().ifPresent(testInstance -> {
      cleanUpStore(testInstance);
      cleanUpUser(testInstance);
    });
  }

  private void cleanUpUser(Object testInstance) {
    UserApi userApi = injector.getInstance(UserApi.class);
    UserDTO userDTO = injector.getInstance(UserDTO.class);
    SuccessResponseDTO successDTO = userApi.deleteUserByUsername(userDTO.getUsername());
    Assertions.assertNotNull(successDTO, "Ошибка: API вернул null!");
    Assertions.assertEquals(userDTO.getUsername(), successDTO.getMessage(), "Имя пользователя не совпадает!");
  }

  private void cleanUpStore(Object testInstance) {
    StoreApi storeApi = injector.getInstance(StoreApi.class);
    StoreDTO storeDTO = injector.getInstance(StoreDTO.class);
    SuccessResponseDTO successDTO = storeApi.deleteByOrderID(String.valueOf(storeDTO.getId()));
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(String.valueOf(storeDTO.getId()), successDTO.getMessage());
  }
}

package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import services.PetStoreApi;
import services.StoreApi;
import services.UserApi;

public class GuiceModule extends AbstractModule {
  @Singleton
  @Provides
  public UserApi getUserApi() {
    return new UserApi();
  }

  @Singleton
  @Provides
  public PetStoreApi getPetStoreApi() {
    return new PetStoreApi();
  }

  @Singleton
  @Provides
  public StoreApi getStoreApi() {
    return new StoreApi();
  }
}

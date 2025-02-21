package extentions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import specifications.Specification;

public class Extension implements BeforeEachCallback {
  private Injector injector;

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    injector = Guice.createInjector(new GuiceModule());
    injector.injectMembers(extensionContext.getTestInstance().get());
    Specification.installSpecification(Specification.requestSpecification(System.getProperty("base.url")), Specification.responseSpecification(200));
  }
}

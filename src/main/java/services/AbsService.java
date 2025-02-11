package services;

import static io.restassured.RestAssured.given;

import annotaitions.Path;
import exceptions.PathDTOException;
import io.restassured.http.Method;

public abstract class AbsService<T, V> {
  private String baseURL = System.getProperty("base.url");

  public String pathUser(String pathVar) {
    return "/" + pathVar;
  }

  protected String getPath() {
    Class<? extends AbsService> clazz = getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = clazz.getDeclaredAnnotation(Path.class);
      return path.value().startsWith("/") ? path.value() : "/" + path.value();
    }
    else {
      return "";
    }
  }

  public String getFullPath() {
    String path = getPath();
    if (path.isEmpty()) {
      throw new PathDTOException();
    }
    return baseURL + path;
  }

  public V createByMethodPost(V dto, Class<V> dtoClass) {
    return given()
        .body(dto)
        .when()
        .post(getPath())
        .then()
        .log().all()
        .extract().as(dtoClass);
  }

  public V requestByMethod(String path, Method method, Class<V> responseClass) {
    return given()
        .when()
        .request(method, getPath() + path)
        .then()
        .log().all()
        .extract().as(responseClass);
  }


}

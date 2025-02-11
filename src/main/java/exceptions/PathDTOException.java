package exceptions;

public class PathDTOException extends RuntimeException {
  public PathDTOException() {
    super("Path not found!");
  }
}

package service.jwt;

public interface GenerationToken {
  String generationToken(String username, Long id) throws Exception;
}

package repository;

import entity.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClientRepository extends JpaRepository<UserClient, String> {
  UserClient findById(Long id);

  UserClient findByUsername(String name);
}

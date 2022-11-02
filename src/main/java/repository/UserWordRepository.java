package repository;

import entity.UserClient;
import entity.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWordRepository extends JpaRepository<UserWord, String> {
  List<UserWord> findAllByUserClient(UserClient userClient);
}

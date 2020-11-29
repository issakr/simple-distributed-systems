package vs.anzeigetafel.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	@Query(value="SELECT * FROM user WHERE id = ?1",
			nativeQuery = true)
	public User findByid(int id);

	@Query(value="SELECT * FROM user WHERE login = ?1",
			nativeQuery = true)
	public User findByLogin(String login);
}

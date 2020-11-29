package vs.anzeigetafel.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface TafelRepo extends JpaRepository<Tafel, Integer>{
	@Query(value="SELECT * FROM tafel WHERE id = ?1",
			nativeQuery = true)
	public Tafel findByid(int id);
}

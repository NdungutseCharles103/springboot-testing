package rw.ac.rca.gradesclassb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.gradesclassb.models.Item;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{

	boolean existsByName(String name);

	Optional<Item> findByName(String name);

}

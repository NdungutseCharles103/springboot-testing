package rw.ac.rca.gradesclassb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.gradesclassb.models.MathLib;

@Repository
public interface IMathLibRepository extends JpaRepository<MathLib, Integer> {

}

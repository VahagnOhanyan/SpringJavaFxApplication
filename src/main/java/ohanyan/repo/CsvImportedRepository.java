package ohanyan.repo;


import ohanyan.domain.CsvImportedEntity;
import ohanyan.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CsvImportedRepository extends JpaRepository<CsvImportedEntity, String> {




}

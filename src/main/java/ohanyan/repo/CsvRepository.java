package ohanyan.repo;


import ohanyan.domain.CsvEntity;
import ohanyan.domain.CsvEntityPK;
import ohanyan.domain.UserEntity;
import ohanyan.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CsvRepository extends JpaRepository<CsvEntity, CsvEntityPK> {



}

package hu.bme.pgaserver.repository;

import hu.bme.pgaserver.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}

package sjcu.spring.utube.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sjcu.spring.utube.application.port.out.UtuberOutputPort;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.UUID;

@Repository
public interface UtuberRepository extends JpaRepository<Utuber, UUID>, UtuberOutputPort {
}

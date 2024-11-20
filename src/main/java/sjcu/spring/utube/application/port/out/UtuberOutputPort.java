package sjcu.spring.utube.application.port.out;

import sjcu.spring.utube.domain.entities.Utuber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UtuberOutputPort {
    Optional<Utuber> findByUtuberId(UUID utuberId);
    Utuber save(Utuber utuber);
    List<Utuber> findByDeletedAtIsNull();
}

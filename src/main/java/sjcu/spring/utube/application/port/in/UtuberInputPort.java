package sjcu.spring.utube.application.port.in;

import sjcu.spring.utube.adapter.request.UpdateShownUtubeRequest;
import sjcu.spring.utube.adapter.request.UpdateUtuberRequest;
import sjcu.spring.utube.application.port.in.command.CreateUtuberCommand;
import sjcu.spring.utube.application.port.vo.LatestUtubesVo;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.List;
import java.util.UUID;

public interface UtuberInputPort {
    List<Utuber> findUtubers();
    Utuber createUtuber(CreateUtuberCommand command);
    List<LatestUtubesVo> findLatestUtubes();
    Utuber updateShownUtube(UpdateShownUtubeRequest request);
    Utuber toggleUtuberUse(UUID utuberId);
    Utuber toggleUtuberDisUse(UUID utuberId);
    Utuber updateUtuber(UUID utuberId, UpdateUtuberRequest request);
    Utuber deleteUtuber(UUID utuberId);
}

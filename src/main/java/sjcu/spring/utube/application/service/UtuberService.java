package sjcu.spring.utube.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjcu.spring.utube.adapter.request.CreateUtuberRequest;
import sjcu.spring.utube.adapter.request.UpdateShownUtubeRequest;
import sjcu.spring.utube.adapter.request.UpdateUtuberRequest;
import sjcu.spring.utube.application.port.in.UtuberInputPort;
import sjcu.spring.utube.application.port.out.CategoryOutputPort;
import sjcu.spring.utube.application.port.out.UtuberOutputPort;
import sjcu.spring.utube.application.port.vo.LatestUtubesVo;
import sjcu.spring.utube.domain.entities.Utuber;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtuberService implements UtuberInputPort {

    private final UtuberOutputPort utuberOutputPort;
    private final CategoryOutputPort categoryOutputPort;

    @Override
    public List<Utuber> findUtubers() {
        return utuberOutputPort.findByDeletedAtIsNull();
    }

    @Override
    public Utuber createUtuber(CreateUtuberRequest request) {
        var category = categoryOutputPort.findByCategoryId(request.categoryId())
            .orElse(null);

        var utuber = Utuber.builder()
            .utuberName(request.utuberName())
            .utuberUrl(request.utuberUrl())
            .category(category)
            .build();

        return utuberOutputPort.save(utuber);
    }

    @Override
    public List<LatestUtubesVo> findLatestUtubes() {
        var utubers = utuberOutputPort.findByDeletedAtIsNull();

        return utubers.stream()
            .filter(utuber -> !utuber.utubes().isEmpty())
            .map(utuber -> {
                var latestUtube = utuber.latestUtube();

                return LatestUtubesVo.builder()
                    .utuberId(utuber.utuberId())
                    .utuberName(utuber.utuberName())
                    .utubeId(latestUtube.utubeId())
                    .utubeTitle(latestUtube.utubeTitle())
                    .utubeThumbnail(latestUtube.utubeThumbnail())
                    .utubeUrl(latestUtube.utubeUrl())
                    .isShown(latestUtube.isShown())
                    .category(utuber.category().categoryName())
                    .build();
            })
            .toList();
    }

    @Override
    public Utuber updateShownUtube(UpdateShownUtubeRequest request) {
        var utuber = utuberOutputPort.findByUtuberId(request.utuberId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유튜버가 없습니다."));
        utuber.updateShownUtube(request.utubeId());
        utuberOutputPort.save(utuber);

        return utuber;
    }

    @Override
    public Utuber toggleUtuberUse(UUID utuberId) {
        var utuber = utuberOutputPort.findByUtuberId(utuberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유튜버가 없습니다."));
        utuber.toggleUtuberUse();
        utuberOutputPort.save(utuber);

        return utuber;
    }

    @Override
    public Utuber toggleUtuberDisUse(UUID utuberId) {
        var utuber = utuberOutputPort.findByUtuberId(utuberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유튜버가 없습니다."));
        utuber.toggleUtuberDisUse();
        utuberOutputPort.save(utuber);

        return utuber;
    }

    @Override
    public Utuber updateUtuber(UUID utuberId, UpdateUtuberRequest request) {
        var utuber = utuberOutputPort.findByUtuberId(utuberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유튜버가 없습니다."));

        var category = categoryOutputPort.findByCategoryId(request.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        utuber.updateUtuber(
            request.categoryId(),
            request.utuberName(),
            request.utuberUrl(),
            category);
        utuberOutputPort.save(utuber);

        return utuber;
    }

    @Override
    public Utuber deleteUtuber(UUID utuberId) {
        var utuber = utuberOutputPort.findByUtuberId(utuberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유튜버가 없습니다."));

        utuber.delete();
        utuberOutputPort.save(utuber);

        return utuber;
    }
}

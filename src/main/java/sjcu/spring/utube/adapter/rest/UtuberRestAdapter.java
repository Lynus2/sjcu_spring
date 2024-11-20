package sjcu.spring.utube.adapter.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sjcu.spring.utube.adapter.request.CreateUtuberRequest;
import sjcu.spring.utube.adapter.request.UpdateShownUtubeRequest;
import sjcu.spring.utube.adapter.request.UpdateUtuberRequest;
import sjcu.spring.utube.adapter.response.*;
import sjcu.spring.utube.application.port.in.UtuberInputPort;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/utubes")
@Tag(name = "utube")
@RequiredArgsConstructor
public class UtuberRestAdapter {

    private final UtuberInputPort utuberInputPort;

    @GetMapping("")
    @Operation(summary = "유튜버 리스트 조회")
    public ResponseEntity<List<FindUtubersResponse>> findUtubers() {
        var utubers = utuberInputPort.findUtubers();

        return ResponseEntity.ok(
            FindUtubersResponse.builds(utubers)
        );
    }

    @PostMapping("")
    @Operation(summary = "유튜버 등록")
    public ResponseEntity<CreateUtuberResponse> createUtuber(
        @Parameter(name = "createUtuberRequest", required = true) @RequestBody CreateUtuberRequest request
    ) {
        var utuber = utuberInputPort.createUtuber(request);

        return ResponseEntity.ok(
            CreateUtuberResponse.build(utuber));
    }

    @PatchMapping("/{utuberId}/use")
    @Operation(summary = "유튜버 사용여부 활성화")
    public ResponseEntity<ToggleUtuberIsUseResponse> toggleUtuberUse(
        @Parameter(name = "utuberId", required = true) @PathVariable UUID utuberId
    ) {
        var utuber = utuberInputPort.toggleUtuberUse(utuberId);

        return ResponseEntity.ok(
            ToggleUtuberIsUseResponse.build(utuber));
    }

    @PatchMapping("/{utuberId}/disuse")
    @Operation(summary = "유튜버 사용여부 비활성화")
    public ResponseEntity<ToggleUtuberIsUseResponse> toggleUtuberDisUse(
        @Parameter(name = "utuberId", required = true) @PathVariable UUID utuberId
    ) {
        var utuber = utuberInputPort.toggleUtuberDisUse(utuberId);

        return ResponseEntity.ok(
            ToggleUtuberIsUseResponse.build(utuber));
    }

    @GetMapping("/latest")
    @Operation(summary = "모든 유튜버의 최근 영상 하나씩 조회")
    public ResponseEntity<List<FindLatestResponse>> findLatest() {
        var latestUtubesVo = utuberInputPort.findLatestUtubes();

        return ResponseEntity.ok(
            FindLatestResponse.builds(latestUtubesVo));
    }

    @PatchMapping("/{utuberId}/utube/{utubeId}")
    @Operation(summary = "시청 이력 기록")
    public ResponseEntity<UpdateShownUtubeResponse> updateShownUtube(
        @Parameter(name = "utuberId", required = true) @PathVariable UUID utuberId,
        @Parameter(name = "utubeId", required = true) @PathVariable UUID utubeId
    ) {
        var utuber = utuberInputPort.updateShownUtube(
            UpdateShownUtubeRequest.builder()
                .utuberId(utuberId)
                .utubeId(utubeId)
                .build()
        );
        return ResponseEntity.ok(
            UpdateShownUtubeResponse.build(utuber, utubeId)
        );
    }

    @PutMapping("/{utuberId}")
    @Operation(summary = "utuber 수정")
    public ResponseEntity<FindUtubersResponse> updateUtuber(
        @Parameter(name = "utuberId", required = true) @PathVariable UUID utuberId,
        @Parameter(name = "updateUtuberRequest", required = true) @RequestBody UpdateUtuberRequest request
    ) {
        var utuber = utuberInputPort.updateUtuber(utuberId, request);

        return ResponseEntity.ok(
            FindUtubersResponse.build(utuber)
        );
    }

    @DeleteMapping("/{utuberId}")
    @Operation(summary = "utuber 삭제")
    public ResponseEntity<FindUtubersResponse> updateUtuber(
        @Parameter(name = "utuberId", required = true) @PathVariable UUID utuberId
    ) {
        var utuber = utuberInputPort.deleteUtuber(utuberId);

        return ResponseEntity.ok(
            FindUtubersResponse.build(utuber)
        );
    }
}

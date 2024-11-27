package sjcu.spring.utube.adapter.thymeleaf;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sjcu.spring.utube.adapter.response.FindLatestResponse;
import sjcu.spring.utube.application.port.in.UtuberInputPort;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thymeleaf/utubes")
@Tag(name = "Thymeleaf Utube")
public class CategoryThymeleafAdapter {
    private final UtuberInputPort utuberInputPort;

    @GetMapping("/category")
    @Operation(summary = "유튜버 카테고리 화면")
    public String viewCategory(Model model) {
        // 데이터를 조회하여 반환 객체 생성
        var response = FindLatestResponse.builds(
            utuberInputPort.findLatestUtubes()
        );
 //todo: check logic
        // 모델에 반환 객체를 추가
        model.addAttribute("response", response);

        // resources/templates에서 return값과 동일한 파일명의 html을 검색하여 반환
        return "utuber";
    }
}
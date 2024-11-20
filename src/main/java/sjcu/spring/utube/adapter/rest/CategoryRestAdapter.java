package sjcu.spring.utube.adapter.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sjcu.spring.utube.adapter.request.CreateCategoryRequest;
import sjcu.spring.utube.adapter.response.CreateCategoryResponse;
import sjcu.spring.utube.adapter.response.FindCategoriesResponse;
import sjcu.spring.utube.application.port.in.CategoryInputPort;

import java.util.List;

@Controller
@RequestMapping("/api/utubes/category")
@Tag(name = "utube")
@RequiredArgsConstructor
public class CategoryRestAdapter {

    private final CategoryInputPort categoryInputPort;

    @PostMapping("")
    @Operation(summary = "유튜브 카테고리 생성")
    public ResponseEntity<CreateCategoryResponse> createCategory(
        @Parameter(name = "createCategoryRequest", required = true) @RequestBody CreateCategoryRequest request
    ) {
        var category = categoryInputPort.createCategory(request);

        return ResponseEntity.ok(
            CreateCategoryResponse.build(category));
    }

    @GetMapping("")
    @Operation(summary = "유튜브 카테고리 리스트 조회")
    public ResponseEntity<List<FindCategoriesResponse>> findCategories() {
        var categories = categoryInputPort.findCategories();

        return ResponseEntity.ok(
            FindCategoriesResponse.build(categories));
    }
}
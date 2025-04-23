package  com.freelance.xray_backend.api.controller;

import com.freelance.xray_backend.application.dto.slide.SlideRequestDto;
import com.freelance.xray_backend.application.dto.slide.SlideResponseDto;
import com.freelance.xray_backend.application.service.slide.SlideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/slides")
@RequiredArgsConstructor
@Slf4j
public class SlideController {

    private final SlideService slideService;

    // based64 IMPLEM
    @PostMapping
    public ResponseEntity<SlideResponseDto> createSlide(@ModelAttribute SlideRequestDto request) throws IOException {
        SlideResponseDto saveSlides = slideService.saveSlides(request);
        log.info("Slide created successfully: {}", saveSlides);

        return ResponseEntity.ok(saveSlides);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SlideResponseDto> updateSlide(@PathVariable Long id,
                                                        @ModelAttribute SlideRequestDto request) throws IOException {

        SlideResponseDto updatedSlide = slideService.updateSlide(id, request);
        log.info("Slide details updated successfully: {}", updatedSlide);

        return ResponseEntity.ok(updatedSlide);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> getSlide(@PathVariable Long id) {
        SlideResponseDto response = slideService.getSlideById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/case/{id}")
    public ResponseEntity<List<SlideResponseDto>> getSlidesByCaseId(@PathVariable Long id) {
        List<SlideResponseDto> slideList = slideService.getSlidesByCaseId(id);

        return ResponseEntity.ok(slideList);
    }

    @GetMapping("/case/{id}/count")
    public ResponseEntity<Long> getSlideCountByCaseId(@PathVariable Long id) {
        List<SlideResponseDto> slideList = slideService.getSlidesByCaseId(id);

        return ResponseEntity.ok((long) slideList.size());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlideRecord(@PathVariable Long id) {
        slideService.deleteSlideRecord(id);
        log.info("Slide record with ID: {} has been deleted successfully.", id);

        return ResponseEntity.ok("Slide record with ID: " + id + " has been deleted successfully.");
    }


    @GetMapping("/list")
    public ResponseEntity<List<SlideResponseDto>> getAllSlides() {
        List<SlideResponseDto> slides = slideService.getAllSlides();

        return ResponseEntity.ok(slides);
    }

}


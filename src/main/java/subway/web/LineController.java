package subway.web;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.application.LineService;
import subway.application.SectionService;
import subway.dto.LineRequest;
import subway.dto.LineResponse;

import java.net.URI;
import java.util.List;
import subway.dto.SectionAddRequest;
import subway.dto.SectionResponse;

@RestController
@RequestMapping("/lines")
public class LineController {

    private final LineService lineService;
    private final SectionService sectionService;

    public LineController(LineService lineService, SectionService sectionService) {
        this.lineService = lineService;
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse line = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(line);
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> getLines() {
        return ResponseEntity.ok(lineService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineResponse> getLine(@PathVariable Long id) {
        return ResponseEntity.ok(lineService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLine(@PathVariable Long id, @RequestBody LineRequest lineUpdateRequest) {
        lineService.updateLine(id, lineUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        lineService.deleteLineById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/sections")
    public ResponseEntity<SectionResponse> addSection(@PathVariable Long id,
        @RequestBody @Valid SectionAddRequest sectionAddRequest) {
        SectionResponse sectionResponse = sectionService.add(id, sectionAddRequest);
        return ResponseEntity.ok(sectionResponse);
    }

    @DeleteMapping("/{id}/sections")
    public ResponseEntity<Void> removeSection(@PathVariable Long id,
        @RequestParam Long stationId) {
        sectionService.remove(id, stationId);
        return ResponseEntity.noContent().build();
    }
}

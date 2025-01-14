package subway.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.dto.StationRequest;
import subway.dto.StationResponse;
import subway.application.StationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        StationResponse station = stationService.saveStation(stationRequest);
        return ResponseEntity.created(URI.create("/stations/" + station.getId())).body(station);
    }

    @GetMapping
    public ResponseEntity<List<StationResponse>> getStations() {
        return ResponseEntity.ok().body(stationService.findAllStationResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationResponse> getStation(@PathVariable Long id) {
        return ResponseEntity.ok().body(stationService.findStationResponseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStation(@PathVariable Long id, @RequestBody StationRequest stationRequest) {
        stationService.updateStation(id, stationRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStationById(id);
        return ResponseEntity.noContent().build();
    }
}

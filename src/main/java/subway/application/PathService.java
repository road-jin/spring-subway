package subway.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.infrastrucure.SectionRepository;
import subway.infrastrucure.StationRepository;
import subway.domain.Fare;
import subway.domain.ShortestPath;
import subway.domain.Station;
import subway.dto.PathResponse;
import subway.exception.station.StationNotFoundException;

@Service
public class PathService {

    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;

    public PathService(StationRepository stationRepository, SectionRepository sectionRepository) {
        this.stationRepository = stationRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional(readOnly = true)
    public PathResponse searchPaths(Long sourceStationId, Long targetStationId, Integer age) {
        List<Station> stations = stationRepository.findAll();
        Station sourceStation = findStation(stations, sourceStationId);
        Station targetStation = findStation(stations, targetStationId);
        ShortestPath shortestPath = new ShortestPath(stations, sectionRepository.findAll());
        int distance = shortestPath.getDistance(sourceStation, targetStation);
        return PathResponse.of(
            shortestPath.getPaths(sourceStation, targetStation),
            distance,
            new Fare(age, distance));
    }

    private Station findStation(List<Station> stations, Long stationId) {
        return stations.stream()
            .filter(station -> station.isSameId(stationId))
            .findAny()
            .orElseThrow(StationNotFoundException::new);
    }
}

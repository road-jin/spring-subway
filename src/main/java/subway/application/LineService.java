package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.infrastrucure.LineRepository;
import subway.infrastrucure.StationRepository;
import subway.domain.Line;
import subway.dto.LineRequest;
import subway.dto.LineResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest request) {
        Line persistLine = lineRepository.insert(new Line(request.getName(), request.getColor()));
        return LineResponse.from(persistLine);
    }
    
    @Transactional(readOnly = true)
    public List<LineResponse> findAll() {
        return lineRepository.findAll()
            .stream()
            .map(line -> LineResponse.of(line, stationRepository.findAllByLineId(line.getId())))
            .collect(Collectors.toUnmodifiableList());
    }
    @Transactional(readOnly = true)
    public LineResponse findById(Long id) {
        return LineResponse.of(lineRepository.findById(id), stationRepository.findAllByLineId(id));
    }

    @Transactional
    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        lineRepository.update(new Line(id, lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    @Transactional
    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

}

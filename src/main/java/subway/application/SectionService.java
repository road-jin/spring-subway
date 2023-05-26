package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.infrastrucure.LineRepository;
import subway.infrastrucure.SectionRepository;
import subway.infrastrucure.StationRepository;
import subway.domain.Section;
import subway.domain.Sections;
import subway.dto.SectionAddRequest;
import subway.dto.SectionResponse;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public SectionService(SectionRepository sectionRepository, LineRepository lineRepository, StationRepository stationRepository) {
        this.sectionRepository = sectionRepository;
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public SectionResponse add(Long lineId, SectionAddRequest sectionAddRequest) {
        Sections sections = new Sections(sectionRepository.findAllByLineId(lineId));
        Section section = sectionAddRequest.toSection(lineRepository.findById(lineId),
            stationRepository.findById(sectionAddRequest.getUpStationId()),
            stationRepository.findById(sectionAddRequest.getDownStationId()));
        sections.addSection(section);
        return SectionResponse.from(sectionRepository.insert(section));
    }

    @Transactional
    public void remove(Long lineId, Long stationId) {
        Sections sections = new Sections(sectionRepository.findAllByLineId(lineId));
        sections.removeLastSection(stationId);
        sectionRepository.delete(lineId, stationId);
    }
}

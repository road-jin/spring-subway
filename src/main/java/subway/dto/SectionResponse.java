package subway.dto;

import subway.domain.Section;

public class SectionResponse {

    private final long id;
    private final long lineId;
    private final long upStationId;
    private final long downStationId;
    private final int distance;

    public SectionResponse(long id, long lineId, long upStationId, long downStationId,
        int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public static SectionResponse from(Section section) {
        return new SectionResponse(section.getId(), section.getLineId(), section.getUpStationId(),
            section.getDownStationId(), section.getDistance());
    }

    public long getId() {
        return id;
    }

    public long getLineId() {
        return lineId;
    }

    public long getUpStationId() {
        return upStationId;
    }

    public long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }
}

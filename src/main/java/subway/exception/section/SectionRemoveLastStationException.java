package subway.exception.section;

import org.springframework.http.HttpStatus;
import subway.exception.CommonException;

public class SectionRemoveLastStationException extends CommonException {

    private static final String MESSAGE = "지하철 노선의 하행 종점역이 아닙니다.";

    public SectionRemoveLastStationException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}

package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateController {

    public List<LocalDate> generateDates(LocalDate startDate, LocalDate endDate, RepetitionPattern repetitionPattern) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (repetitionPattern.matches(date)) {
                dates.add(date);
            }
        }
        return dates;
    }
}

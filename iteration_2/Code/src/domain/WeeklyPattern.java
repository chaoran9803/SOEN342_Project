package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class WeeklyPattern implements RepetitionPattern {

    private List<DayOfWeek> daysOfWeek;

    public WeeklyPattern(List<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public boolean matches(LocalDate date) {
        return daysOfWeek.contains(date.getDayOfWeek());
    }
}

package domain;

import java.time.LocalDate;

public class MonthlyPattern implements RepetitionPattern {

    private int dayOfMonth;

    public MonthlyPattern(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public boolean matches(LocalDate date) {
        return date.getDayOfMonth() == dayOfMonth;
    }
}

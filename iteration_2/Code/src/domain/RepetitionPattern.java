package domain;

import java.time.LocalDate;

public interface RepetitionPattern {

    boolean matches(LocalDate date);
}

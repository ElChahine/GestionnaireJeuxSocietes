package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class WeekendSummaryCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        WeekendSummaryCommand command = new WeekendSummaryCommand(null, null, 3);
        assertEquals("View Summary (Weekend Special!)", command.getLabel());
    }

    @Test
    void visibilityShouldDependOnDay() {
        WeekendSummaryCommand command = new WeekendSummaryCommand(null, null, 3);
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        boolean expected = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);
        assertEquals(expected, command.isVisible());
    }
}
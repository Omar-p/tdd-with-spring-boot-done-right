package de.rieckpil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeUtilTest {

  @Mock
  TimeProvider timeProvider;

  @InjectMocks
  TimeUtil cut; // cut: Class Under Test

  @Test
  void shouldThrowExceptionWhenDateIsFuture() throws Exception {

    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate creationDate = LocalDate.now().plusDays(1);

    assertThrows(IllegalArgumentException.class, () -> {
      cut.getDiffBetweenCreationDate(creationDate);
    });
  }

  @Test
  void shouldReturnTodayWhenCommentWasCreatedToday() throws Exception {
    // should when
    // given when then
    // should when then
    // when all tests follow this pattern, it acts as a great documentation
    // use the same assertions util for the same file (expected and actual not in the same order, so it may confuse the developer)
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate today = LocalDate.now();

    String result = cut.getDiffBetweenCreationDate(today);

    assertEquals("today", result);

  }


  @Test
  void shouldReturnMoreThanAYearWhenCommentWasCreatedLastYear() throws Exception {
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate lastYear = LocalDate.now().minusYears(2);

    String result = cut.getDiffBetweenCreationDate(lastYear);

    assertEquals("more than a year", result);
  }


  @Test
  void shouldReturnOneMonthAgoWhenCommentWasCreatedOneMonthAgo() throws Exception {
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

    String result = cut.getDiffBetweenCreationDate(oneMonthAgo);

    assertEquals("one month ago", result);

  }

  @Test
  void shouldReturnXMonthsAgoWhenCommentWasCreatedXMonthsAgo() throws Exception {
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate xMonthsAgo = LocalDate.now().minusMonths(3);

    String result = cut.getDiffBetweenCreationDate(xMonthsAgo);

    assertEquals("3 months ago", result);

  }

  @Test
  void shouldReturnOneDayAgoWhenCommentWasCreatedYesterday() throws Exception {
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate oneDayAgo = LocalDate.now().minusDays(1);

    String result = cut.getDiffBetweenCreationDate(oneDayAgo);

    assertEquals("one day ago", result);

  }

  @Test
  void shouldReturnXDaysAgoWhenCommentWasCreatedXDaysAgo() throws Exception {
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());

    LocalDate xDaysAgo = LocalDate.now().minusDays(3);

    String result = cut.getDiffBetweenCreationDate(xDaysAgo);

    assertEquals("3 days ago", result);

  }
}

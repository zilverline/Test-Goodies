/**
 * Copyright (C) 2011 Zilverline B.V. <info@zilverline.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zilverline.jodatime;

import static org.junit.Assert.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runners.model.Statement;

public class FixedDateTest {

  private LocalDate expectedLocalDate;
  private Statement statement;
  private MethodRule methodRule;
  private DateTime expectedDateTime;

  private final class ExceptionThrowingStatement extends Statement {

    @Override
    public void evaluate() throws Throwable {
      throw new RuntimeException();
    }

  }
  private static final class MockLocalDateStatement extends Statement {
    private final LocalDate expected;
    public boolean called;

    private MockLocalDateStatement(LocalDate expected) {
      this.expected = expected;
    }

    @Override
    public void evaluate() throws Throwable {
      assertEquals(expected, new LocalDate());
      this.called = true;
    }
  }
  private static final class MockDateTimeStatement extends Statement {
    private final DateTime expected;
    public boolean called;
    
    private MockDateTimeStatement(DateTime expected) {
      this.expected = expected;
    }
    
    @Override
    public void evaluate() throws Throwable {
      assertEquals(expected, new DateTime());
      this.called = true;
    }
  }

  @Before
  public void setUp() throws Exception {
    expectedLocalDate = new LocalDate(2010, 1, 3);
    expectedDateTime = new DateTime(2010, 1, 3, 9, 23, 10, 35);
  }

  private void evaluateRule() throws Throwable {
    methodRule.apply(statement, null, null).evaluate();
  }

  @Test
  public void fix_date_on_date_time() throws Throwable {
    methodRule = new FixedDate(expectedDateTime);
    statement = new MockDateTimeStatement(expectedDateTime);

    evaluateRule();

    assertTrue("mock not called", MockDateTimeStatement.class.cast(statement).called);
  }
  @Test
  public void fix_date_on_local_date() throws Throwable {
    methodRule = new FixedDate(expectedLocalDate);
    statement = new MockLocalDateStatement(expectedLocalDate);
    
    evaluateRule();
    
    assertTrue("mock not called", MockLocalDateStatement.class.cast(statement).called);
  }

  @Test
  public void reset_date_after_test() throws Throwable {
    methodRule = new FixedDate(expectedLocalDate);
    statement = new MockLocalDateStatement(expectedLocalDate);

    evaluateRule();

    assertFalse("localdate should have been reset after test", expectedLocalDate.getYear() == new LocalDate().getYear());
  }

  @Test
  public void always_resets_date_after_test() throws Throwable {
    methodRule = new FixedDate(expectedLocalDate);
    statement = new ExceptionThrowingStatement();
    try {
      evaluateRule();
      fail("should have thrown exception.");
    } catch (RuntimeException expected) {
      assertFalse("localdate should have been reset after test", expectedLocalDate.getYear() == new LocalDate().getYear());
    }
  }
}

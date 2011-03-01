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

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Rule that Fixes the date of a test on a given {@link LocalDate} or {@link DateTime} for systems using joda-time.
 * It will reset the date to the system's date using the {@link DateTimeUtils#setCurrentMillisSystem()}.
 * Usage:
 * <pre>
 *  private LocalDate endOfYear = new LocalDate(2010, 12, 31);
 *  &#064;Rule
 *  public FixedDate fixedDate = new FixedDate(endOfYear);
 * </pre>
 * Check for examples: {@link FixedDateWithDateTimeRuleTest} and {@link FixedDateWithLocalDateRuleTest}. 
 */
public class FixedDate implements MethodRule {

  private final DateTime fixDateOn;

  public FixedDate(LocalDate fixDateOn) {
    this.fixDateOn = fixDateOn.toDateTimeAtStartOfDay();
  }

  public FixedDate(DateTime fixDateOn) {
    this.fixDateOn = fixDateOn;
  }

  public Statement apply(final Statement base, FrameworkMethod method, Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        DateTimeUtils.setCurrentMillisFixed(fixDateOn.getMillis());
        try {
          base.evaluate();
        } finally {
          DateTimeUtils.setCurrentMillisSystem();
        }
      }
    };
  }

}

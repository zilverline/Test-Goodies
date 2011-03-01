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
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

public class FixedDateWithLocalDateRuleTest {
  private static LocalDate lastWeek = new LocalDate().minusDays(7);
  
  @Rule
  public FixedDate fixedDate = new FixedDate(lastWeek);
  
  @AfterClass
  public static void after() throws Exception {
    assertEquals(new DateTime().getDayOfYear(), lastWeek.plusDays(7).getDayOfYear());
  }
  
  @Test
  public void date_should_be_fixed() throws Exception {
    assertEquals(lastWeek, new LocalDate());
  }
}

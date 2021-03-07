package com.dotty.challenge.repository

import com.dotty.challenge.{Interval, MonthInterval, Report}

//It's not really needed in this little exercise... but hiding a repository implementation behind a trait is
//always good practice.
trait Repository {
  def summarizeOrdersByRange(interval: Interval, monthIntervals: List[MonthInterval]): Report
}

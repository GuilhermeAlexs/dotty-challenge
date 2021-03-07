package com.dotty

import com.dotty.challenge.model.Order

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//Package with all needed types, definitions etc.
package object challenge {
  type Interval      = (LocalDateTime, LocalDateTime)
  type MonthInterval = (Int, Int)
  type Report        = Map[MonthInterval, Int]
  type DataGenerator = {def generateOrders: List[Order]}

  val projectDatePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}

package com.dotty.challenge.parsers

import com.dotty.challenge.{Interval, MonthInterval, projectDatePattern}

import java.time.LocalDateTime

import scala.util.chaining.scalaUtilChainingOps
import scala.util.Try

object RequestParser {
  def extractInterval(args: Array[String]): Try[Interval] = Try {
    val start = LocalDateTime.parse(args(0), projectDatePattern)
    val end = LocalDateTime.parse(args(1), projectDatePattern)
    (start, end)
  }

  def extractMonthIntervals(args: Array[String]): Try[List[MonthInterval]] = Try {
    args.drop(2).map(stringToMonthInterval).toList
  }

  private def stringToMonthInterval(str: String): MonthInterval =
    if (str.contains(">"))
      (str.trim.drop(1).toInt + 1, Int.MaxValue)
    else
      str.split("-").pipe(p => (p(0).toInt, p(1).toInt))
}

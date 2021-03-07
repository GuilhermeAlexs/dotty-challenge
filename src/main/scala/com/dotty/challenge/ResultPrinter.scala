package com.dotty.challenge

import scala.util.chaining.scalaUtilChainingOps

object ResultPrinter {
  def printResult(report: Report): Unit =
    report
      .tap(_ => println("\n----- RESULTS -----"))
      .map {
        case (interval, nOrders) if interval._2 == Int.MaxValue =>
          s">${interval._1 - 1}: $nOrders orders"
        case (interval, nOrders)                                =>
          s"${interval._1} - ${interval._2}: $nOrders orders"
      }.toList.sorted.foreach(println)

  def printWrongArgumentsError(error: Throwable): Unit =
    println(
      s"""\n${error.getMessage}. Usage: <jar-name>.jar <start> <end> <month_interval1> <month_interval2> ...
         | <start>           : Start date (format: yyyy-MM-dd HH:mm:ss)
         | <end>             : Start date (format: yyyy-MM-dd HH:mm:ss)
         | <month_intervalN> : Month interval (format X-Y or >Z. Example: 1-3, >12)
         | Example           : <jar-name>.jar "2020-01-01 12:00:00" "2021-01-01 12:00:00" "1-6" "7-12"
         |""".stripMargin
    )

  def printUnknownError(error: Throwable): Unit = println(s"Ops... something went terribly wrong: $error")
}
package com.dotty.challenge.repository

import com.dotty.challenge._

import java.time.LocalDateTime

import scala.util.chaining.scalaUtilChainingOps

//InMemory version of our Repository. It receives a DataGenerator custom type that creates customers orders.
class InMemoryRepository(generator: DataGenerator) extends Repository {
  private val orders = generator.generateOrders

  override def summarizeOrdersByRange(interval: Interval, monthIntervals: List[MonthInterval]): Report =
    orders
      .flatMap(_.items)
      .groupMapReduce(item => intervalOf(interval._1, item.product.creationDate, monthIntervals))(_ => 1)(_+_)
      .removed((0,0))
      .pipe(report => monthIntervals.map(_ -> 0).toMap ++ report)

  private def intervalOf(startDate: LocalDateTime, prodDate: LocalDateTime,
                         monthIntervals: List[MonthInterval]): MonthInterval =
    monthIntervals.find{ interval =>
      prodDate.isAfter(startDate.plusMonths(interval._1 - 1)) &&
      (interval._2 == Int.MaxValue || prodDate.isBefore(startDate.plusMonths(interval._2 + 1)))
    }.getOrElse((0,0))
}

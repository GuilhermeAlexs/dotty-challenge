package com.dotty.challenge

import com.dotty.challenge.parsers.RequestParser
import com.dotty.challenge.repository.{InMemoryRepository, RandomDataGenerator, Repository}

import scala.util.chaining.scalaUtilChainingOps

object Main extends App {
  try {
    //Internally (13, Int.MaxValue) = >12
    val defaultIntervals = List((1,3),(4,6),(7,12),(13,Int.MaxValue))

    val interval = RequestParser
      .extractInterval(args)
      .getOrElse(throw new IllegalArgumentException("Can't parse interval"))

    val monthIntervals = RequestParser
      .extractMonthIntervals(args)
      .getOrElse(throw new IllegalArgumentException("Can't parse month intervals"))
      .pipe(customIntervals => if (customIntervals.nonEmpty) customIntervals else defaultIntervals)

    println("Executing with random data...")

    //Creating a in-memory repository with random data. Check InMemoryRepositoryTest to see it working
    //with static data.
    val repo: Repository = new InMemoryRepository(RandomDataGenerator)

    repo
      .summarizeOrdersByRange(interval, monthIntervals)
      .pipe(ResultPrinter.printResult)
  } catch {
    case e: IllegalArgumentException => ResultPrinter.printWrongArgumentsError(e)
    case e: Exception                => ResultPrinter.printUnknownError(e)
  }
}

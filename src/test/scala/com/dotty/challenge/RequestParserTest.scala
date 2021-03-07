package com.dotty.challenge

import java.time.LocalDateTime

import com.dotty.challenge.parsers.RequestParser
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class RequestParserTest extends AnyFlatSpec with Matchers {
  "A valid request" should "be correctly parsed" in {
    val args             = Array("2020-01-01 12:00:00", "2021-01-01 12:00:00", "1-3", "7-12")
    val mIntervals       = RequestParser.extractMonthIntervals(args)
    val interval         = RequestParser.extractInterval(args)
    val expectedInterval = (LocalDateTime.parse(args(0),projectDatePattern), LocalDateTime.parse(args(1),projectDatePattern))

    mIntervals.get mustEqual Array((1,3),(7,12))
    interval.get   mustEqual expectedInterval
  }

  "A valid request without month intervals" should "be correctly parsed" in {
    val args       = Array("2020-01-01 12:00:00")
    val mIntervals = RequestParser.extractMonthIntervals(args)
    mIntervals.get mustEqual Array()
  }

  "A invalid request (wrong number of arguments)" should "throw an exception" in {
    val args = Array("2020-01-01 12:00:00")
    an [Exception] should be thrownBy RequestParser.extractInterval(args).get
  }

  "A invalid request (unexpected datetime format)" should "throw an exception" in {
    val args = Array("01-01-2020 12:00:00", "2020-01-01 12:00:00")
    an [Exception] should be thrownBy RequestParser.extractInterval(args).get
  }
}

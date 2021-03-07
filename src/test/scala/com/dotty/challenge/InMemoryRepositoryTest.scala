package com.dotty.challenge

import com.dotty.challenge.model.{Category, Customer, Item, Order, Product}
import com.dotty.challenge.repository.InMemoryRepository
.gitignore
import java.time.LocalDateTime

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class InMemoryRepositoryTest extends AnyFlatSpec with Matchers {
  val tRef: LocalDateTime = LocalDateTime.parse("2020-01-01 12:00:00", projectDatePattern)

  object StaticDataGenerator {
    def generateOrders: List[Order] = {
      val theCostumer = Customer("Guilherme Alexsander", "galexs@gmail.com")
      val products    = List(
        Product("Lusiadas",          Category.BOOK,      weight = 0.8, tRef.plusMonths(1)),
        Product("On the Road",       Category.BOOK,      weight = 0.8, tRef.plusMonths(4)),
        Product("Lenovo Notebook",   Category.ELETRONIC, weight = 0.8, tRef.plusMonths(4)),
        Product("Black Label 12yrs", Category.DRINK,     weight = 0.8, tRef.plusMonths(8)),
        Product("T-Shirt Nike ",     Category.CLOTHING,  weight = 0.8, tRef.plusMonths(13))
      )
      val items = products.map(Item(price = 50, shippingFee = 10, taxAmount = 0.1, _))

      List.range(0, items.length).map { i =>
        Order(theCostumer, "Street 1 Apartment 25", grandTotal = 50, tRef.plusMonths(i), List(items(i)))
      }
    }
  }

  "InMemoryDataRepository" should "should correctly summarize orders by month intervals" in {
    val interval       = (tRef, tRef.plusYears(1))
    val monthIntervals = List((1,3),(4,6),(7,12))
    val report         = new InMemoryRepository(StaticDataGenerator).summarizeOrdersByRange(interval, monthIntervals)

    report((1,3))  mustEqual 1
    report((4,6))  mustEqual 2
    report((7,12)) mustEqual 1
  }

  "InMemoryDataRepository" should "should correctly summarize orders by month intervals (1 month interval)" in {
    val interval       = (tRef, tRef.plusYears(1))
    val monthIntervals = List((1,3))
    val report         = new InMemoryRepository(StaticDataGenerator).summarizeOrdersByRange(interval, monthIntervals)

    report((1,3))  mustEqual 1
  }

  "InMemoryDataRepository" should "should correctly summarize orders by month intervals (with >N)" in {
    val interval       = (tRef, tRef.plusYears(1))
    val monthIntervals = List((1,3),(4,6),(7,Int.MaxValue))
    val report         = new InMemoryRepository(StaticDataGenerator).summarizeOrdersByRange(interval, monthIntervals)

    report((1,3))  mustEqual 1
    report((4,6))  mustEqual 2
    report((7,Int.MaxValue)) mustEqual 2
  }
}

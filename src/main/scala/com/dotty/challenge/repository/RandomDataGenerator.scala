package com.dotty.challenge.repository

import com.dotty.challenge.model.{Category, Customer, Item, Order, Product}

import java.time.LocalDateTime

import scala.collection.parallel.CollectionConverters._
import scala.util.Random
import scala.util.chaining.scalaUtilChainingOps

//Generates thousand of costumers orders to populate ao in-memory repository.
object RandomDataGenerator {
  def generateOrders: List[Order] = generateProducts.pipe(generateItems).pipe(generateOrders)

  private def generateProducts: List[Product] = LocalDateTime.now.pipe { now =>
    List(
      Product("Lenovo Notebook 8Gb",   Category.ELETRONIC, weight = 2.2,  now.minusYears(2)),
      Product("Lenovo Notebook 16Gb",  Category.ELETRONIC, weight = 2.5,  now.minusYears(1)),
      Product("Acer Notebook 4Gb",     Category.ELETRONIC, weight = 2.3,  now.minusYears(1)),
      Product("Acer Notebook 16Gb",    Category.ELETRONIC, weight = 2.5,  now.minusYears(1)),
      Product("HP Notebook 8Gb",       Category.ELETRONIC, weight = 2.8,  now.minusYears(1)),
      Product("Samsung S9",            Category.ELETRONIC, weight = 0.9,  now.minusMonths(5)),
      Product("Samsung S7",            Category.ELETRONIC, weight = 0.9,  now.minusMonths(5)),
      Product("Tablet Xiaomi",         Category.ELETRONIC, weight = 1.3,  now.minusMonths(2)),
      Product("Glen Moray 15 years",   Category.DRINK,     weight = 1.25, now.minusMonths(1)),
      Product("Black Label 12 years",  Category.DRINK,     weight = 1.2,  now.minusMonths(1)),
      Product("Green Label 15 years",  Category.DRINK,     weight = 1.2,  now.minusMonths(1)),
      Product("Lusiadas",              Category.BOOK,      weight = 1,    now.minusMonths(1)),
      Product("On The Road",           Category.BOOK,      weight = 1,    now.minusMonths(2)),
      Product("Life On The Missisipi", Category.BOOK,      weight = 1,    now.minusMonths(2)),
      Product("Confissão de Lúcio",    Category.BOOK,      weight = 1.1,  now.minusMonths(3)),
      Product("Poesias Reunidas",      Category.BOOK,      weight = 1,    now.minusMonths(3)),
      Product("Shirt Long-fu XL",      Category.CLOTHING,  weight = 0.4,  now.minusMonths(1)),
      Product("Shirt Timberlad XXL",   Category.CLOTHING,  weight = 0.4,  now.minusMonths(1)),
      Product("Jeans Long-fu 42",      Category.CLOTHING,  weight = 0.6,  now.minusMonths(1)),
      Product("T-shirt Nike M",        Category.CLOTHING,  weight = 0.4,  now.minusMonths(1))
    )
  }

  private def generateItems(products: List[Product]): List[Item] = products.map { prod =>
    prod.category match {
      case Category.ELETRONIC =>
        Item(price = 600 + Random.nextInt(1000), shippingFee = 20, taxAmount = 0.2, product = prod)
      case Category.BOOK =>
        Item(price = 10 + Random.nextInt(20), shippingFee = 5, taxAmount = 0.05, product = prod)
      case Category.DRINK =>
        Item(price = 50 + Random.nextInt(150), shippingFee = 10, taxAmount = 0.3, product = prod)
      case Category.CLOTHING =>
        Item(price = 10 + Random.nextInt(50), shippingFee = 5, taxAmount = 0.1, product = prod)
    }
  }

  private def generateCustomers: List[Customer] =
    List.range(1, 2000).map(i => s"Someone $i").map(name => Customer(s"$name", s"${name.toLowerCase}@gmail.com"))

  private def generateAddress: String =
    Random.pipe(rnd => s"Street ${rnd.nextInt(500)} Apartment ${rnd.nextInt(100)}")

  //Here I'm using a parallel collection to speed-up creation of many customers orders.
  private def generateOrders(items: List[Item]): List[Order] = generateCustomers.par.map { customer =>
    val now      = LocalDateTime.now
    val maxItems = Random.nextInt(20)
    val order    = Order(
      customer        = customer,
      shippingAddress = generateAddress,
      grandTotal      = 0d,
      orderDate       = now.minusMonths(Random.nextInt(12)),
      items           = Nil
    )

    List.range(0, maxItems).foldLeft(order) { (order, _) =>
      val newItem = items(Random.nextInt(items.length))
      order.copy(grandTotal = order.grandTotal + newItem.price, items = order.items :+ newItem)
    }
  }.toList
}

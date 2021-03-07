package com.dotty.challenge.model

import java.time.LocalDateTime

case class Order(customer: Customer,
                 shippingAddress: String,
                 grandTotal: Double,
                 orderDate: LocalDateTime,
                 items: List[Item])

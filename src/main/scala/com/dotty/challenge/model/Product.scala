package com.dotty.challenge.model

import java.time.LocalDateTime

case class Product(name: String, category: Category, weight: Double, creationDate: LocalDateTime)

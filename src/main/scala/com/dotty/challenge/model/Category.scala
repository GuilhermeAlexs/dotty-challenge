package com.dotty.challenge.model

trait Category {def value: String}

object Category {
  case object BOOK      extends Category {val value = "book"}
  case object CLOTHING  extends Category {val value = "clothing"}
  case object DRINK     extends Category {val value = "drink"}
  case object ELETRONIC extends Category {val value = "eletronic"}
}


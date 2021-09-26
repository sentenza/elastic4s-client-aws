package com.sksamuel.elastic4s.aws

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StringToSignTest extends AnyWordSpec with Matchers with SharedTestData {

  "StringToSign" should {

    val result =
      """AWS4-HMAC-SHA256
        |20150830T123600Z
        |20150830/us-east-1/es/aws4_request
        |4f4206baa1babbea405d47f83cfd8262f06b89b6f3ad187be581a3e5f09d40c8""".stripMargin

    "be able to build instance from region, service and canonicalRequest " in {

      val canonicalRequest = CanonicalRequest(httpGetRequest)
      val stringToSign = StringToSign(service, region, canonicalRequest, date, dateTime)
      stringToSign.toString shouldBe (result)
    }
  }
}
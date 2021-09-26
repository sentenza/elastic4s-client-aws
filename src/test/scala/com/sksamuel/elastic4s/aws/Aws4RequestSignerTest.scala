package com.sksamuel.elastic4s.aws

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import software.amazon.awssdk.auth.credentials.{AwsBasicCredentials, AwsCredentialsProvider, AwsSessionCredentials, StaticCredentialsProvider}
import software.amazon.awssdk.regions.Region

class Aws4RequestSignerTest extends AnyWordSpec with Matchers with SharedTestData {

  "Aws4RequestSigner" should {

    val result =
      """AWS4-HMAC-SHA256 Credential=AKIDEXAMPLE/20150830/us-east-1/es/aws4_request, SignedHeaders=content-type;host;x-amz-date, Signature=1cd028739dd9da8786adc77de75d00f36ae1b3a2b76f13195cd7bea6af500032""".stripMargin

    "be able to add amazon compliant authentication header" in {

      val chainProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(awsKey, awsSecret))
      val signer = new Aws4TestRequestSigner(chainProvider, region, date, dateTime)
      val withHeaders = signer.withAws4Headers(httpPostRequest)
      withHeaders.getAllHeaders find (_.getName == "Authorization") match {
        case Some(header) => header.getValue shouldBe (result)
        case _            => 1 shouldBe (0)
      }
    }

    "be able to add security Token Header if there is a session key in context" in {
      val credentials = AwsSessionCredentials.create(awsKey, awsSecret, awsSessionToken)
      val chainProvider = StaticCredentialsProvider.create(credentials)
      val signer = new Aws4TestRequestSigner(chainProvider, region, date, dateTime)
      val withHeaders = signer.withAws4Headers(httpPostRequest)
      withHeaders.getAllHeaders find (_.getName == "X-Amz-Security-Token") match {
        case Some(header) => header.getValue shouldBe (awsSessionToken)
        case _            => 1 shouldBe (0)
      }
    }

    "be able to add date time header when none is found" ignore {
      val credentials = AwsSessionCredentials.create(awsKey, awsSecret, awsSessionToken)
      val chainProvider = StaticCredentialsProvider.create(credentials)
      val signer = new Aws4TestRequestSigner(chainProvider, region, date, dateTime)

      val withHeaders = signer.withAws4Headers(httpPostRequestWithoutDate)
      withHeaders.getAllHeaders find (_.getName == "Authorization") match {
        case Some(header) => header.getValue shouldBe (result)
        case _            => 1 shouldBe (0)
      }
    }

    "be able to clean bad Host headers" ignore {
      val credentials = AwsSessionCredentials.create(awsKey, awsSecret, awsSessionToken)
      val chainProvider = StaticCredentialsProvider.create(credentials)
      val signer = new Aws4TestRequestSigner(chainProvider, region, date, dateTime)

      val withHeaders = signer.withAws4Headers(httpPostRequestWithBadHost)
      withHeaders.getAllHeaders find (_.getName == "Authorization") match {
        case Some(header) => header.getValue shouldBe (result)
        case _            => 1 shouldBe (0)
      }
    }
  }

  class Aws4TestRequestSigner(awsCredentialProvider: AwsCredentialsProvider, region: Region, date: String, dateTime: String)
    extends Aws4RequestSigner(awsCredentialProvider, region) {
    override def buildDateAndDateTime() = (date, dateTime)
  }

}

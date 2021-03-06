/*
 * Copyright 2015-2016 IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package packages.samples

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import common.{ TestHelpers, Wsk, WskProps, WskTestHelpers }
import spray.json._
import spray.json.DefaultJsonProtocol._

@RunWith(classOf[JUnitRunner])
class CurlTest extends TestHelpers with WskTestHelpers {
    implicit val wskprops = WskProps()
    val wsk = new Wsk()
    val greetingAction = "/whisk.system/samples/curl"

    behavior of "samples curl"

    it should "Return Could not resolve host when sending no parameter" in {
        val expectedError = "Could not resolve host"
        val run = wsk.action.invoke(greetingAction, Map())
        withActivation(wsk.activation, run) {
            _.response.result.get.toString should include(expectedError)
        }
    }

    it should "Return the web content when sending the public google as the payload" in {
        val expectedBody = "<HTML>"
        val run = wsk.action.invoke(greetingAction, Map("payload" -> "google.com".toJson))
        withActivation(wsk.activation, run) {
            _.response.result.get.toString should include(expectedBody)
        }
    }
}

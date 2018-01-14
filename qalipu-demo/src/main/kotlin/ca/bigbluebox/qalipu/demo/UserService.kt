/*
 * Copyright 2018 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.bigbluebox.qalipu.demo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@JsonIgnoreProperties(ignoreUnknown = true)
class User {
    var id: Int? = null
    var name: String? = null
    var username: String? = null
    var phone: String? = null
    var website: String? = null
}

@Service
class UserService {

    fun listUsers(): List<User> {
        val rt = RestTemplate()
        val resp = rt.exchange("https://jsonplaceholder.typicode.com/users", HttpMethod.GET,
                null, object : ParameterizedTypeReference<List<User>>() {})
        return resp.body
    }
}
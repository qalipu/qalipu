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

package ca.bigbluebox.qalipu

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.vaadin.spring.i18n.MessageProvider
import org.vaadin.spring.i18n.ResourceBundleMessageProvider

/**
 * I18n configuration for Qalipu.
 */
@Configuration
class I18NConfiguration {

    /**
     * Declares a ResourceBundleMessageProvider for ca.bigbluebox.galipu.messages.
     */
    @Bean
    fun qalipuMessageProvider(): MessageProvider =
            ResourceBundleMessageProvider("ca.bigbluebox.qalipu.messages")
}
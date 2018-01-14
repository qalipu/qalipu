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

import org.springframework.stereotype.Component
import org.vaadin.spring.sidebar.annotation.SideBarSection
import org.vaadin.spring.sidebar.annotation.SideBarSections

/**
 * Declares the Operations side bar section.
 */
@Component
@SideBarSections(
        SideBarSection(id = Sections.OPERATIONS, captionCode = "qalipu.operations", order = 10000)
)
class Sections {

    companion object {
        const val OPERATIONS = "qalipu.operations"
    }
}
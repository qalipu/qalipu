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

import com.github.vok.karibudsl.expandRatio
import com.github.vok.karibudsl.horizontalLayout
import com.github.vok.karibudsl.panel
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewDisplay
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.spring.annotation.SpringViewDisplay
import com.vaadin.ui.Component
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.vaadin.spring.security.VaadinSecurity
import org.vaadin.spring.sidebar.components.ValoSideBar
import org.vaadin.spring.sidebar.security.VaadinSecurityItemFilter

/**
 * Marker interface for the main UI.
 * If a bean implementing this interface is found, it will replace DefaultMainUI.
 */
interface MainUI


/**
 * This class is the default UI.
 * Unlike DefaultHomeView, this is not a placeholder, it is fully functional.
 *
 * It provides a ValoSideBar and a SpringViewDisplay.
 *
 * To provide a different UI, create a bean implementing the MainUI marker interface.
 */
@SpringUI
@SpringViewDisplay
@ConditionalOnMissingBean(MainUI::class)
class DefaultMainUI(val vaadinSecurity: VaadinSecurity,
                    val valoSideBar: ValoSideBar) : UI(), ViewDisplay, MainUI {
    private lateinit var springViewDisplay: Panel

    override fun init(request: VaadinRequest?) {
        valoSideBar.itemFilter = VaadinSecurityItemFilter(vaadinSecurity)
        horizontalLayout {
            setSizeFull()
            addComponent(valoSideBar)

            springViewDisplay = panel {
                expandRatio = 1f
                setSizeFull()
            }
        }
    }

    override fun showView(view: View?) {
        springViewDisplay.content = view as Component
    }
}

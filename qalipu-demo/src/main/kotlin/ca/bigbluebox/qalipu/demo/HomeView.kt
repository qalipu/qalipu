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

import ca.bigbluebox.qalipu.HomeView
import com.github.vok.karibudsl.label
import com.vaadin.icons.VaadinIcons
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.vaadin.spring.sidebar.annotation.SideBarItem
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon
import javax.annotation.PostConstruct


@SpringComponent
@SideBarItem(sectionId = Sections.DEMO, caption = "Home")
@VaadinFontIcon(VaadinIcons.HOME)
class HomeAction : Runnable {
    override fun run() {
        UI.getCurrent().navigator.navigateTo("")
    }
}

@SpringView(name = "")
class HomeView : HomeView, VerticalLayout() {

    @PostConstruct
    fun init() {
        label("Welcome to Qalipu demo.") {
            styleName = ValoTheme.LABEL_H1
        }
    }
}
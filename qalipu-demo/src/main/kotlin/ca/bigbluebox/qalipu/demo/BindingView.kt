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

import com.github.vok.karibudsl.formLayout
import com.github.vok.karibudsl.label
import com.github.vok.karibudsl.panel
import com.github.vok.karibudsl.textField
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.server.Page
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Label
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import org.vaadin.spring.sidebar.annotation.SideBarItem
import javax.annotation.PostConstruct

@SpringComponent
@SideBarItem(sectionId = Sections.DEMO, caption = "Binding")
class BindingAction : Runnable {
    override fun run() {
        UI.getCurrent().navigator.navigateTo(BindingView.VIEW_NAME)
    }
}

@SpringView(name = BindingView.VIEW_NAME)
class BindingView : VerticalLayout(), View {
    lateinit var label: Label

    @PostConstruct
    fun init() {
        panel("Binding demo") {
            setMargin(true)
            formLayout {
                textField("Input") {
                    addValueChangeListener { label.value = (it.component as TextField).value }
                }
                label = label(""){
                    caption = "Output"
                }
            }
        }
    }

    override fun enter(event: ViewChangeListener.ViewChangeEvent?) {
        Page.getCurrent().setTitle("Binding")
    }

    companion object {
        const val VIEW_NAME = "binding-view"
    }
}
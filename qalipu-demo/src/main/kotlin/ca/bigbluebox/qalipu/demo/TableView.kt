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

import com.github.vok.karibudsl.*
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
@SideBarItem(sectionId = Sections.DEMO, caption = "Table")
class TableAction : Runnable {
    override fun run() {
        UI.getCurrent().navigator.navigateTo(TableView.VIEW_NAME)
    }
}

@SpringView(name = TableView.VIEW_NAME)
class TableView(val userService: UserService) : VerticalLayout(), View {
    @PostConstruct
    fun init() {
        setSizeFull()
        panel("Table demo") {
            setMargin(true)
            setSizeFull()
            grid<User> {
                setSizeFull()
                setItems(userService.listUsers())
                addColumn(User::name, { it ?: "" }) {
                    caption = "Name"

                }
                addColumn(User::phone).caption = "Phone"
                addColumn(User::website).caption = "Website"
            }
        }
    }

    companion object {
        const val VIEW_NAME = "table-view"
    }
}
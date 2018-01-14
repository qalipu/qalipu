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

import com.github.vok.karibudsl.label
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.server.Page
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.vaadin.spring.i18n.I18N
import javax.annotation.PostConstruct

/**
 * Marker interface for the Home view.
 * If you provide a Spring bean implementing this interface, the default
 * implementation (DefaultHomeView) won't be used.
 */
interface HomeView: View

/**
 * Default home view.
 * A bean implementing HomeView should be created to replace this placeholder.
 */
@SpringView(name = "")
@ConditionalOnMissingBean(HomeView::class)
class DefaultHomeView(val i18n: I18N) : HomeView, VerticalLayout() {

    @PostConstruct
    fun init() {
        label(i18n.get("qalipu.defaultHomeView.header")) {
            styleName = ValoTheme.LABEL_H1
        }
        label(i18n.get("qalipu.defaultHomeView.detail"))
    }

    override fun enter(event: ViewChangeListener.ViewChangeEvent?) {
        Page.getCurrent().setTitle(i18n.get("qalipu.defaultHomeView.title"))
    }
}

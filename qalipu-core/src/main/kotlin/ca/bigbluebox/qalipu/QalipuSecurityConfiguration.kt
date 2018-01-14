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

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.WebSecurityConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration
import org.vaadin.spring.security.shared.VaadinLogoutHandler
import org.vaadin.spring.security.shared.VaadinRedirectLogoutHandler
import org.vaadin.spring.security.web.VaadinRedirectStrategy

/**
 * Configure Spring Security with Keycloak and Vaadin.
 */
@Configuration
@EnableWebSecurity
@EnableVaadinSharedSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@ConditionalOnMissingBean(WebSecurityConfigurer::class)
@Import(DefaultMainUI::class, Sections::class, LogoutAction::class, DefaultHomeView::class)
class QalipuSecurityConfiguration : KeycloakWebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(keycloakAuthenticationProvider())
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
        http.formLogin().disable()
        http.anonymous().disable()
        http.csrf().disable()
//        http.csrf().requireCsrfProtectionMatcher(keycloakCsrfRequestMatcher())
        http
                .authorizeRequests()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                .anyRequest().authenticated()
        http
                .logout()
                .addLogoutHandler(keycloakLogoutHandler())
                .logoutUrl("/sso/logout").permitAll()
                .logoutSuccessUrl("/")
        http
                .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter::class.java)
                .addFilterBefore(keycloakAuthenticationProcessingFilter(), BasicAuthenticationFilter::class.java)
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
        http
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
    }

    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers("/VAADIN/**")
    }

    @Bean
    fun KeycloakConfigResolver(): KeycloakConfigResolver {
        return KeycloakSpringBootConfigResolver()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    fun keycloakAuthenticationProcessingFilterRegistrationBean(
            filter: KeycloakAuthenticationProcessingFilter): FilterRegistrationBean {
        val registrationBean = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    fun keycloakPreAuthActionsFilterRegistrationBean(
            filter: KeycloakPreAuthActionsFilter): FilterRegistrationBean {
        val registrationBean = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean(name = [(VaadinSharedSecurityConfiguration.VAADIN_LOGOUT_HANDLER_BEAN)])
    internal fun vaadinLogoutHandler(vaadinRedirectStrategy: VaadinRedirectStrategy): VaadinLogoutHandler {
        val handler = VaadinRedirectLogoutHandler(vaadinRedirectStrategy)
        handler.logoutUrl = "sso/logout"
        return handler
    }
}

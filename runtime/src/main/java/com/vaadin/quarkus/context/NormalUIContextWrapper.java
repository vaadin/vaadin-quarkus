/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.quarkus.context;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

import java.lang.annotation.Annotation;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableContext;

import com.vaadin.quarkus.annotation.NormalUIScoped;
import com.vaadin.quarkus.annotation.UIScoped;

/**
 * Used to bind multiple scope annotations to a single context. Will delegate
 * all context-related operations to it's underlying instance, apart from
 * getting the scope of the context.
 */
public class NormalUIContextWrapper implements InjectableContext {

    @Override
    public Class<? extends Annotation> getScope() {
        return NormalUIScoped.class;
    }

    @Override
    public <T> T get(final Contextual<T> component,
            final CreationalContext<T> creationalContext) {
        return getContext().get(component, creationalContext);
    }

    @Override
    public <T> T get(final Contextual<T> component) {
        return getContext().get(component);
    }

    @Override
    public boolean isActive() {
        return getContext().isActive();
    }

    @Override
    public void destroy(final Contextual<?> contextual) {
        getContext().destroy(contextual);
    }

    @Override
    public void destroy() {
        getContext().destroy();
    }

    @Override
    public ContextState getState() {
        return getContext().getState();
    }

    /**
     * Gets a wrapped injectable context.
     * <p>
     * It's not private only for testing purpose.
     * 
     * @return a wrapped context
     */
    InjectableContext getContext() {
        return Arc.container().getActiveContext(UIScoped.class);
    }

}

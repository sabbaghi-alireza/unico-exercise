/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unico.exercise.rest;

import org.apache.shiro.web.jaxrs.ShiroFeature;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is the Java EE 7 "no XML" approach to activating
 * JAX-RS.
 * <p>
 * <p>
 * Resources are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
    /* class body intentionally left blank */

    @Inject
    ElementRestService elementRestService;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // register Shiro
        classes.add(ShiroFeature.class);

        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        singletons.add(elementRestService);

        return singletons;
    }
}

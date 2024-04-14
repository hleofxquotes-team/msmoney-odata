/**
 * Copyright (c) 2015 SDL Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sdl.odata.example.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.edm.registry.ODataEdmRegistry;
import com.sdl.odata.example.Location;
import com.sdl.odata.example.Person;
import com.sdl.odata.example.datasource.InMemoryDataSource;

/**
 * @author rdevries
 */
@Component
public class EntityServiceRegistar {
    private static final Logger LOG = LoggerFactory.getLogger(EntityServiceRegistar.class);

    @Autowired
    private ODataEdmRegistry oDataEdmRegistry;

    @Autowired
    private InMemoryDataSource<Person> personsDataSource;

    @Autowired
    private InMemoryDataSource<Location> locationsDataSource;

    @PostConstruct
    public void registerEntities() throws ODataException
    {
        LOG.debug("Registering example entities");

        oDataEdmRegistry.registerClasses(Lists.newArrayList(Person.class, Location.class, GetAverageAge.class));

        DataSourcesUtils.addPersons(personsDataSource);

        DataSourcesUtils.addLocations(locationsDataSource);
    }
}

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

import java.util.List;

import com.google.common.collect.Lists;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.example.Location;
import com.sdl.odata.example.Person;
import com.sdl.odata.example.datasource.InMemoryDataSource;

public class DataSourcesUtils {

    static final void addPersons(InMemoryDataSource<Person> dataSource) throws ODataException
    {
        List<Person> persons = Lists.newArrayList(new Person("MyHero", "Darkwing", "Duck", 23),
                new Person("Sidekick", "Launchpad", "McQuack", 35), new Person("Waddlemeyer", "Gosalyn", "Mallard", 9));

        for (Person person : persons) {
            dataSource.create(null, person, null);
        }
    }

    static final void addLocations(InMemoryDataSource<Location> dataSource) throws ODataException
    {
        List<Location> locations = Lists.newArrayList(new Location("1", "40.714", "-74.006"),
                new Location("2", "-73.99698494189342", "40.751505821326134"),
                new Location("3", "-73.92695712775627", "-73.92695712775627"));
        for (Location address : locations) {
            dataSource.create(null, address, null);
        }
    }

}

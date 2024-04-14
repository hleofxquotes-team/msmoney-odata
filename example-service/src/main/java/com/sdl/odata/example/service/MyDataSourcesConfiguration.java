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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.sdl.odata.example.Location;
import com.sdl.odata.example.Person;
import com.sdl.odata.example.datasource.InMemoryDataSource;
import com.sdl.odata.example.datasource.InMemoryDataSourceProvider;

/**
 * @author rdevries
 */
@Configuration
@ComponentScan
public class MyDataSourcesConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MyDataSourcesConfiguration.class);

    @Bean
    public InMemoryDataSource<Person> personsDataSource()
    {
        return new InMemoryDataSource<Person>() {
            @Override
            public Class<Person> getEntityJavaType()
            {
                return Person.class;
            }
        };
    }

    @Bean
    public InMemoryDataSourceProvider<Person> personsDataSourceProvider()
    {
        return new InMemoryDataSourceProvider<Person>(personsDataSource());
    }

    @Bean
    public InMemoryDataSource<Location> locationsDataSource()
    {
        return new InMemoryDataSource<Location>() {
            @Override
            public Class<Location> getEntityJavaType()
            {
                return Location.class;
            }
        };
    }

    @Bean
    public InMemoryDataSourceProvider<Location> locationsDataSourceProvider()
    {
        return new InMemoryDataSourceProvider<Location>(locationsDataSource());
    }

}

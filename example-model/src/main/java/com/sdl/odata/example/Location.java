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
package com.sdl.odata.example;

import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * @author rdevries
 */
@EdmEntity(namespace = "SDL.OData.Example", key = "id", containerName = "SDLExample")
@EdmEntitySet
public class Location implements MyEntity {

    @EdmProperty(name = "id", nullable = false)
    private String id;

    @EdmProperty(name = "latitude", nullable = false)
    private String latitude;

    @EdmProperty(name = "longtitude", nullable = false)
    private String longtitude;

    public Location(String id, String latitude, String longtitude)
    {
        super();
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Location()
    {
    }

    public String getLongtitude()
    {
        return longtitude;
    }

    public void setLongtitude(String zipcode)
    {
        this.longtitude = zipcode;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }
}

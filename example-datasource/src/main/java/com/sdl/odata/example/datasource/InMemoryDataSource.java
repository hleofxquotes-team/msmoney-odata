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
package com.sdl.odata.example.datasource;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.ODataSystemException;
import com.sdl.odata.api.edm.model.EntityDataModel;
import com.sdl.odata.api.parser.ODataUri;
import com.sdl.odata.api.parser.ODataUriUtil;
import com.sdl.odata.api.processor.datasource.DataSource;
import com.sdl.odata.api.processor.datasource.ODataDataSourceException;
import com.sdl.odata.api.processor.datasource.TransactionalDataSource;
import com.sdl.odata.api.processor.link.ODataLink;
import com.sdl.odata.api.service.ODataRequestContext;
import com.sdl.odata.example.MyEntity;

import scala.Option;

/**
 * @author rdevries
 * @param <T>
 */
// @Component
public abstract class InMemoryDataSource<T extends MyEntity> implements DataSource {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDataSource.class);

    private ConcurrentMap<String, T> concurrentMap = new ConcurrentHashMap<>();

    @Override
    public Object create(ODataUri oDataUri, Object o, EntityDataModel entityDataModel) throws ODataException
    {
        T entity = (T) o;
        if (concurrentMap.putIfAbsent(entity.getId(), entity) != null) {
            throw new ODataDataSourceException("Could not create entity, already exists");
        }

        return entity;
    }

    @Override
    public Object update(ODataUri oDataUri, Object o, EntityDataModel entityDataModel) throws ODataException
    {
        T entity = (T) o;
        if (concurrentMap.containsKey(entity.getId())) {
            concurrentMap.put(entity.getId(), entity);

            return entity;
        }
        else {
            throw new ODataDataSourceException("Unable to update entity, entity does not exist");
        }
    }

    @Override
    public void delete(ODataUri oDataUri, EntityDataModel entityDataModel) throws ODataException
    {
        Option<Object> entity = ODataUriUtil.extractEntityWithKeys(oDataUri, entityDataModel);
        if (entity.isDefined()) {
            T e = (T) entity.get();
            concurrentMap.remove(e.getId());
        }
    }

    @Override
    public TransactionalDataSource startTransaction()
    {
        throw new ODataSystemException("No support for transactions");
    }

    public ConcurrentMap<String, T> getConcurrentMap()
    {
        return concurrentMap;
    }

    @Override
    public void createLink(ODataUri oDataUri, ODataLink oDataLink, EntityDataModel entityDataModel) throws ODataException
    {

    }

    @Override
    public void deleteLink(ODataUri oDataUri, ODataLink oDataLink, EntityDataModel entityDataModel) throws ODataException
    {

    }

    public boolean isSuitableFor(ODataRequestContext oDataRequestContext, String entityType)
    {
        Class<?> javaType = oDataRequestContext.getEntityDataModel().getType(entityType).getJavaType();
        // LOG.info("isSuitableFor - dataSource={}", this.getClass());
        // LOG.info("isSuitableFor - entityType={}", javaType);
        return javaType.equals(getEntityJavaType());
    }

    public abstract Class<T> getEntityJavaType();
}

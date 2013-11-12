/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except  in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 ******************************************************************************/
package org.usergrid.rest;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.usergrid.cassandra.CassandraResource;
import org.usergrid.cassandra.Concurrent;
import org.usergrid.rest.applications.ApplicationRequestCounterIT;
import org.usergrid.rest.applications.DevicesResourceIT;
import org.usergrid.rest.applications.assets.AssetResourceIT;
import org.usergrid.rest.applications.collection.PagingResourceIT;
import org.usergrid.rest.applications.EventsResourceIT;
import org.usergrid.rest.applications.users.*;
import org.usergrid.rest.filters.ContentTypeResourceIT;
import org.usergrid.rest.management.organizations.AdminEmailEncodingIT;
import org.usergrid.rest.management.organizations.OrganizationResourceIT;
import org.usergrid.rest.management.organizations.OrganizationsResourceIT;
import org.usergrid.rest.management.users.organizations.UsersOrganizationsResourceIT;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
          ActivityResourceIT.class,
          AdminEmailEncodingIT.class,
          ApplicationRequestCounterIT.class,
          AssetResourceIT.class,
          BasicIT.class,
          CollectionsResourceIT.class,
          ContentTypeResourceIT.class,
          DevicesResourceIT.class,
          EventsResourceIT.class,
          GroupResourceIT.class,
          OrganizationResourceIT.class,
          OrganizationsResourceIT.class,
          OwnershipResourceIT.class,
          PagingResourceIT.class,
          PermissionsResourceIT.class,
          UserResourceIT.class,
          UsersOrganizationsResourceIT.class
        })
@Concurrent()
public class RestITSuite {

  @ClassRule
  public static CassandraResource cassandraResource = CassandraResource.newWithAvailablePorts();
}

package org.usergrid;


import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.usergrid.cassandra.CassandraResource;
import org.usergrid.cassandra.Concurrent;
import org.usergrid.management.EmailFlowIT;
import org.usergrid.management.OrganizationIT;
import org.usergrid.management.RoleIT;
import org.usergrid.management.cassandra.ApplicationCreatorIT;
import org.usergrid.management.cassandra.ManagementServiceIT;
import org.usergrid.security.providers.FacebookProviderIT;
import org.usergrid.security.providers.PingIdentityProviderIT;
import org.usergrid.services.ActivitiesServiceIT;
import org.usergrid.services.ApplicationsServiceIT;
import org.usergrid.services.CollectionServiceIT;
import org.usergrid.services.ConnectionsServiceIT;
import org.usergrid.services.GroupServiceIT;
import org.usergrid.services.RolesServiceIT;
import org.usergrid.services.ServiceFactoryIT;
import org.usergrid.services.ServiceInvocationIT;
import org.usergrid.services.ServiceRequestIT;
import org.usergrid.services.UsersServiceIT;


@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ActivitiesServiceIT.class, ApplicationCreatorIT.class, ApplicationsServiceIT.class,
                CollectionServiceIT.class, ConnectionsServiceIT.class, ManagementServiceIT.class, EmailFlowIT.class,
                FacebookProviderIT.class, GroupServiceIT.class, OrganizationIT.class, PingIdentityProviderIT.class,
                RoleIT.class, RolesServiceIT.class, ServiceRequestIT.class, ServiceFactoryIT.class,
                ServiceInvocationIT.class, UsersServiceIT.class
        })
@Concurrent()
public class ServiceITSuite {
    @ClassRule
    public static CassandraResource cassandraResource = CassandraResource.newWithAvailablePorts();
}

package org.usergrid.security.providers;


import java.util.Map;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.usergrid.ServiceITSetup;
import org.usergrid.ServiceITSetupImpl;
import org.usergrid.ServiceITSuite;
import org.usergrid.cassandra.ClearShiroSubject;
import org.usergrid.cassandra.Concurrent;
import org.usergrid.management.OrganizationInfo;
import org.usergrid.management.UserInfo;
import org.usergrid.persistence.entities.Application;
import org.usergrid.persistence.entities.User;
import org.usergrid.utils.MapUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/** @author zznate */
@Concurrent()
public class FacebookProviderIT {

    private static SignInProviderFactory providerFactory;
    private static UUID applicationId;

    @Rule
    public ClearShiroSubject clearShiroSubject = new ClearShiroSubject();

    @ClassRule
    public static ServiceITSetup setup = new ServiceITSetupImpl( ServiceITSuite.cassandraResource );


    @BeforeClass
    public static void setup() throws Exception {
        providerFactory = ServiceITSuite.cassandraResource.getBean( SignInProviderFactory.class );
        UserInfo adminUser = setup.getMgmtSvc()
                                  .createAdminUser( "fbuser", "Facebook User", "user@facebook.com", "test", false,
                                          false );
        OrganizationInfo organization = setup.getMgmtSvc().createOrganization( "fb-organization", adminUser, true );
        applicationId = setup.getMgmtSvc().createApplication( organization.getUuid(), "fb-application" ).getId();
    }


    @Test
    @Ignore
    public void verifyGetOrCreateOk() throws Exception {
        Application application = setup.getEmf().getEntityManager( applicationId ).getApplication();
        Map fb_user = MapUtils.hashMap( "id", "12345678" ).map( "name", "Facebook User" ).map( "username", "fb.user" );

        FacebookProvider facebookProvider = ( FacebookProvider ) providerFactory.facebook( application );

        String fb_access_token = "CAAE...NJIZD";
        User user1 = facebookProvider.createOrAuthenticate( fb_access_token );

        assertNotNull( user1 );
    }


    @Test
    public void verifyConfigureOk() throws Exception {
        Application application = setup.getEmf().getEntityManager( applicationId ).getApplication();
        Map fbProps = MapUtils.hashMap( "api_url", "localhost" );
        FacebookProvider fp = ( FacebookProvider ) providerFactory.facebook( application );
        assertNotNull( fp );

        fp.saveToConfiguration( "facebookProvider", fbProps );

        fp.configure();

        Map map = fp.loadConfigurationFor( "facebookProvider" );
        assertEquals( "localhost", map.get( "api_url" ) );
    }
}

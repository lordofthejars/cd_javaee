package org.superbiz;

import org.apache.cxf.jaxrs.client.WebClient;
import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

// tag::init[]
@RunWith(Arquillian.class)
public class FullColorServiceIntegrationTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClasses(ColorService.class);
    }

    @ArquillianResource
    private URL webappUrl;


    @Test
    @RunAsClient
    public void getRandom() throws Exception {

        // GET
        {
            final WebClient webClient = WebClient.create(webappUrl.toURI());
            final Response response = webClient.path("color/random").get();

            assertThat(response.getStatus(), is(200));

            final String content = slurp((InputStream) response.getEntity());

            assertThat(content, CoreMatchers.anyOf(is("red"), is("blue")));
        }

    }

    public static String slurp(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        out.flush();
        return new String(out.toByteArray());
    }
}
// end::init[]
package org.superbiz;

import org.apache.cxf.jaxrs.client.WebClient;
import org.hamcrest.core.Is;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Arquillian.class)
public class ColorServiceIntegrationTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClasses(ColorService.class, Color.class);
    }

    @ArquillianResource
    private URL webappUrl;


    @Test @RunAsClient
    public void postAndGet() throws Exception {

        // POST
        {
            final WebClient webClient = WebClient.create(webappUrl.toURI());
            final Response response = webClient.path("color/green").post(null);

            assertThat(response.getStatus(), is(204));
        }

        // GET
        {
            final WebClient webClient = WebClient.create(webappUrl.toURI());
            final Response response = webClient.path("color").get();

            assertThat(response.getStatus(), is(200));

            final String content = slurp((InputStream) response.getEntity());

            assertThat(content, is("green"));
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

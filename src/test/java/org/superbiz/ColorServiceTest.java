package org.superbiz;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.hamcrest.core.Is;
import org.junit.Test;

public class ColorServiceTest {

    @Test
    public void setAndGetColor() {
        ColorService colorService = new ColorService();

        colorService.setColor("green");
        assertThat(colorService.getColor(), is("green"));
    }

}

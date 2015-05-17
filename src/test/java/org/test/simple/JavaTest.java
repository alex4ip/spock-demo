package org.test.simple;

import org.junit.Test;

public class JavaTest {
    @Test
    public void testWtf() throws Exception {

        double v1 = 2.0 - 1.1;
        double v2 = 3.0 - 1.1;

        //region assertions
        assert String.valueOf(v1).startsWith("0.8999");
        assert v2 == 1.9;
        //endregion
    }

    /**
     * {@link org.test}
     */
}

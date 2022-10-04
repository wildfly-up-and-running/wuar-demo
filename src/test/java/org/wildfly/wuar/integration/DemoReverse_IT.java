/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2020, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.wuar.integration;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class DemoReverse_IT {

    private static DockerImageName imageName = DockerImageName.parse(System.getProperty("imageName"));

    @Rule
    public GenericContainer server = new GenericContainer(imageName)
            .withEnv("DEMO_DEFAULT_TEXT", "Hello From Test!")
            .withExposedPorts(8080);

    private URI endpoint;

    @Before
    public void setUp() throws URISyntaxException {
        String address = server.getHost();
        Integer port = server.getFirstMappedPort();

        endpoint = new URI("http://" + address + ":" + port);
    }

    @Test
    public void testReverseWithNoParam() {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(endpoint)
                    .path("/reverse")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("!tseT morF olleH", response.readEntity(String.class));

        } finally {
            client.close();
        }
    }

    @Test
    public void testReverseWithParam() {

        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(endpoint)
                    .path("/reverse")
                    .queryParam("text", "Does it work?")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("?krow ti seoD", response.readEntity(String.class));

        } finally {
            client.close();
        }
    }
}

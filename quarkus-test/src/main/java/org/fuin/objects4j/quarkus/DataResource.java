package org.fuin.objects4j.quarkus;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.CREATED;

/**
 * REST resource providing the data.
 */
@Path("/data")
@Transactional
public class DataResource {

    @Inject
    EntityManager em;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(Data data) {
        if (em.find(Data.class, data.id) == null) {
            em.persist(data);
            return Response.status(CREATED).build();
        }
        return Response.status(422, "Entity already exists").build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read(@PathParam("id") String id) {
        final Data data = em.find(Data.class, id);
        if (data == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(data).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        final Data data = em.find(Data.class, id);
        if (data == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(data);
        return Response.ok().build();
    }

}

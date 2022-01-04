package com.fernanda.resources;

import com.fernanda.entities.Seller;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.Objects;

import static javax.ws.rs.core.Response.Status.*;

@Path("sellers")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SellerResource {

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(@RestPath Long id) {
        return Seller.findById(id)
                .map( seller -> Objects.nonNull(seller)
                    ? Response.ok(seller).build()
                    : Response.ok().status(NOT_FOUND).build());
    }

    @POST
    public Uni<Response> create(Seller seller) {
        if (seller == null || seller.id != null) throw new WebApplicationException("Id was invalid.", 422);
        return Panache.withTransaction(seller::persist)
                .replaceWith(Response.ok(seller).status(CREATED)::build);
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@RestPath Long id, Seller seller) {
        if (seller == null || seller.name == null) throw new WebApplicationException("Name was invalid.", 422);
        return Panache.withTransaction(() -> Seller.<Seller> findById(id)
                .onItem().ifNotNull().invoke(entity -> entity.name = seller.name)
        )
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> Seller.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }
}

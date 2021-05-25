package boundary;

import entity.ShoppingCart;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/carts")
public class ShoppingCartResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCarts() {
        return ShoppingCart.getAllShoppingCarts()
                .onItem().transform(shoppingcarts -> Response.ok(shoppingcarts))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getSingleCart(@PathParam("id") Long id) {
        return ShoppingCart.findByShoppingCartId(id)
                .onItem().ifNotNull().transform(cart -> Response.ok(cart).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> createShoppingCart(ShoppingCart shoppingCart) {
        if (shoppingCart == null || shoppingCart.name == null) {
            throw new WebApplicationException("ShoppingCart name was not set on request.", 422);
        }
        return ShoppingCart.createShoppingCart(shoppingCart)
                .onItem().transform(id -> URI.create("/v1/carts/" + id.id))
                .onItem().transform(uri -> Response.created(uri))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @PUT
    @Path("{cartid}/{productid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam("cartid") Long id, @PathParam("productid") Long product) {
        return ShoppingCart.addProductToShoppingCart(id, product)
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);

    }

    @DELETE
    @Path("{cartid}/{productid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> delete(@PathParam("cartid") Long id, @PathParam("productid") Long product) {
        return ShoppingCart.deleteProductFromShoppingCart(id, product)
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }
}

package com.example.tendersystem.resourcess;

import com.example.tendersystem.model.Tender;
import com.example.tendersystem.service.TenderService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tenders")
public class TenderResource {
    private TenderService tenderService = new TenderService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tender> getAllTenders() {
        return tenderService.getAllTenders();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTender(Tender tend){
        Tender newTender = tenderService.createTender(tend);
        return Response.status(Response.Status.CREATED).entity(newTender).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTenderById(@PathParam("id") int id) {
        Tender tender = tenderService.getTenderById(id);
        if (tender != null) {
            return Response.ok(tender).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeStatus(@PathParam("id") int id, @QueryParam("newStatus") String newStatus) {
        boolean isUpdated = tenderService.updateTenderStatus(id, newStatus);

        if (isUpdated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTenders(@QueryParam("query") String query) {
        if (query == null || query.trim().isEmpty()) {
            return Response.ok(tenderService.getAllTenders()).build();
        }

        List<Tender> tenders = tenderService.searchTenders(query);
        return Response.ok(tenders).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTender(@PathParam("id") int id) {
        boolean isDeleted = tenderService.deleteTender(id);

        if (isDeleted) {
            return Response.ok().build(); // Статус 200 OK
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Помилка при видаленні тендеру")
                    .build();
        }
    }

    @PUT
    @Path("/{id}/select-executor")
    public Response selectExecutor(@PathParam("id") int tenderId, @QueryParam("executorId") int executorId) {
        boolean isUpdated = tenderService.selectExecutor(tenderId, executorId);

        if (isUpdated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Помилка").build();
        }
    }
}
package com.example.tendersystem;

import com.example.tendersystem.model.Tender;
import com.example.tendersystem.model.TenderProposal;
import com.example.tendersystem.service.TenderService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("/tenders")
public class TenderResource {
    private TenderService tenderService = new TenderService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tender> getAllTenders() {
        return tenderService.getAllTenders();
    }

    @GET
    @Path("/{id}/tendProposals")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TenderProposal> getProposals(@PathParam("id") int tenderId) {
        return tenderService.getAllProposalsTenderId(tenderId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTender(Tender tend){
        Tender newTender = tenderService.createTender(tend);
        return Response.status(Response.Status.CREATED).entity(newTender).build();
    }

    @POST
    @Path("/{id}/tendProposals")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProposal(@PathParam("id") int tenderId, TenderProposal proposal) {
        TenderProposal newProposal = tenderService.createTendProposals(tenderId, proposal);
        return Response.status(Response.Status.CREATED).entity(newProposal).build();
    }

//    @DELETE
//    @Path("/{id}")
//    public Response deleteTender(@PathParam("id") int id) {
//        tenderService.deleteTender(id);
//        return Response.noContent().build();
//    }
}
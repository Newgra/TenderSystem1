package com.example.tendersystem.resourcess;

import com.example.tendersystem.model.TenderProposal;
import com.example.tendersystem.service.ProposalService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/proposals")
public class ProposalResource {
    private final ProposalService proposalService = new ProposalService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response applyForTender(TenderProposal proposal) {
        boolean isCreated = proposalService.createProposal(proposal);

        if (isCreated) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            // Замість INTERNAL_SERVER_ERROR повертаємо BAD_REQUEST з описом помилки
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Не вдалося подати пропозицію. Можливо, ви вже подавали заявку або тендер закритий!\"}")
                    .build();
        }
    }

    @GET
    @Path("/tender/{tenderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProposals(@PathParam("tenderId") int tenderId) {
        java.util.List<TenderProposal> proposals = proposalService.getProposalsByTenderId(tenderId);
        return Response.ok(proposals).build();
    }
}
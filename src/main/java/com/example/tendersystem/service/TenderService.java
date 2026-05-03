package com.example.tendersystem.service;

import com.example.tendersystem.model.Tender;
import com.example.tendersystem.model.TenderProposal;

import java.util.ArrayList;
import java.util.List;

public class TenderService {
    private static List<Tender> tenders = new ArrayList<>();
    private static List<TenderProposal> tendProposals = new ArrayList<>();

    private static int newId = 1;
    private static int newProposalId = 1;


    public Tender createTender(Tender tend){
        tend.setId(newId++);
        tenders.add(tend);
        return tend;
    }

    public List<Tender> getAllTenders(){
        return tenders;
    }

    public TenderProposal createTendProposals(int tenderId, TenderProposal tenderProposal){
        tenderProposal.setId(newProposalId++);
        tenderProposal.setTenderId(tenderId);
        tendProposals.add(tenderProposal);
        return tenderProposal;
    }

    public List<TenderProposal> getAllProposalsTenderId(int tenderId){
        List<TenderProposal> result = new ArrayList<>();
        for (TenderProposal p : tendProposals) {
            if (p.getTenderId() == tenderId) {
                result.add(p);
            }
        }
        return result;
    }

    public void deleteTender(int id) {
        tenders.removeIf(t -> t.getId() == id);
    }
}

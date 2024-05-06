package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;
    private final FarmRepository farmRepository;
    private final AuthRepository authRepository;

    //ADMIN
    public List<Contract> getAllContract(){
        if (contractRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return contractRepository.findAll();
    }
    //khaled company create contract
    public void createContract(Integer companyId, Contract contract){
        Company company = companyRepository.findCompanyById(companyId);
        if(contract.getContractEndDate().isAfter(contract.getContractStartingDate())){
            throw new ApiException("end date can't be before start date");
        }
        Contract contract1= new Contract(null,contract.getContractDetails(),"waiting",contract.getContractStartingDate(),contract.getContractEndDate(),company,null);
        company.getContracts().add(contract);
        companyRepository.save(company);
        contractRepository.save(contract);
    }

    //khaled
    //cancel contract when no farm accepted contract;
    public void cancelContract(Integer companyId, Integer contractId){
        Contract contract =  contractRepository.findContractById(contractId);
        if(contract == null){
            throw new ApiException("contract not found");
        }
        if(contract.getCompany().getId() != companyId){
            throw new ApiException("invalid request");
        }
        if(!contract.getStatus().equalsIgnoreCase("waiting")) {
            throw new ApiException("can not cancel contract");
        }
        contract.setStatus("Canceled");
    }

    //-------------------------------------   end CRUD  ---------------------------
//sara
    public List<Contract> newContract(Integer userId) {
        ArrayList<Contract> Contract2 = new ArrayList<>();
        List<Contract> Contract3 =  contractRepository.findAll();

        if (Contract3== null) {
            throw new ApiException(" Contracts not found");
        }
        for (Contract Contract1 : Contract3) {
            if (Contract1.getStatus().equalsIgnoreCase("waiting")  ){
                Contract2.add(Contract1);
            }}

        return Contract2;
    }

    public List<Contract> NewContract() {

        List<Contract> Contract3 = contractRepository.newContract();

        if (Contract3== null) {
            throw new ApiException(" orders not found");
        }

        return Contract3;
    }

    //sara
    public void acceptContract(Integer farmId, Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) {
            throw new ApiException("contract id not found");
        }
        contract.setStatus("accepted");
        contractRepository.save(contract);

    }
    //sara
    public List<Contract> currentContract(Integer userId) {
        ArrayList<Contract> Contract2 = new ArrayList<>();
        List<Contract> Contract3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

         if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
             Contract3 = contractRepository.findContractByCompanyId(company.getId());
             if (Contract3== null) {
                 throw new ApiException(" orders not found");
             }
             for (Contract Contract1 : Contract3) {

                 if (Contract1.getStatus().equalsIgnoreCase("in progress") ||  Contract1.getStatus().equalsIgnoreCase("accepted") || Contract1.getStatus().equalsIgnoreCase("waiting") ){
                     Contract2.add(Contract1);
                 }}
        }
        else if (user.getRole().equalsIgnoreCase("FARM")) {
            Farm farm= farmRepository.findFarmById(userId);
             Contract3 =  contractRepository.findContractByFarmId(farm.getId());

             if (Contract3== null) {
                 throw new ApiException(" orders not found");
             }
             for (Contract Contract1 : Contract3) {

                 if (Contract1.getStatus().equalsIgnoreCase("in progress") ||  Contract1.getStatus().equalsIgnoreCase("accepted")  ){
                     Contract2.add(Contract1);
                 }}
        }


        return Contract2;
    }
    //sara
    public List<Contract> previousContract(Integer userId) {
        ArrayList<Contract> Contract2 = new ArrayList<>();
        List<Contract> Contract3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            Contract3 = contractRepository.findContractByCompanyId(company.getId());
        }
        else if (user.getRole().equalsIgnoreCase("FARM")) {
            Farm farm= farmRepository.findFarmById(userId);
            Contract3 =  contractRepository.findContractByFarmId(farm.getId());;
        }
        if (Contract3== null) {
            throw new ApiException(" orders not found");
        }
        for (Contract Contract1 : Contract3) {
            if (Contract1.getStatus().equalsIgnoreCase("finished")||Contract1.getStatus().equalsIgnoreCase("Canceled")){
                Contract2.add(Contract1);
            }}

        return Contract2;
    }



}

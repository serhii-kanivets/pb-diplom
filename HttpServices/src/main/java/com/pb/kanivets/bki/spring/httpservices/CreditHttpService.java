package com.pb.kanivets.bki.spring.httpservices;

import com.pb.kanivets.bki.core.entities.Credit;
import com.pb.kanivets.bki.core.services.ICreditService;
import com.pb.kanivets.bki.core.wrappers.CreditsListWrapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CreditHttpService implements ICreditService {

    private final RestTemplateRequestor requestor = new RestTemplateRequestor();

    @Override
    public List<Credit> listCredits() {
        return requestor.doRequest("credits", CreditsListWrapper.class).getList();
    }

    @Override
    public List<Credit> listCredits(int clientId) {
        return requestor.doRequest("credits/ofclient/" + clientId, CreditsListWrapper.class).getList();
    }

    @Override
    public Credit getCredit(int creditId) {
        return requestor.doRequest("credits/" + creditId, Credit.class);
    }

    @Override
    public Credit addCredit(Credit credit) {
        return requestor.doPostRequest("credits", credit);
    }

    @Override
    public void updateCredit(Credit newInfo) {
        System.out.println("update request");
        requestor.doPostRequest("credits/" + newInfo.getId(), newInfo);
        System.out.println("update complete");
    }

}

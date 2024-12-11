package com.example.gestiontransactions.request;

import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.model.Virement;
import lombok.Data;

@Data
public class VirementRequest {
    private Virement virement;
    private SMS sms;


}
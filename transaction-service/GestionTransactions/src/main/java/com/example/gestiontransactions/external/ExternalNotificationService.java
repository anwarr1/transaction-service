package com.example.gestiontransactions.external;

import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.dto.SendVerificationCodeRequest;
import com.example.gestiontransactions.dto.SendVerificationCodeResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "NOTIFICATION-SERVICE",url="http://192.168.91.205:9090/api/notifications")

public interface ExternalNotificationService {

    @GetMapping("/test")
    public ResponseEntity<String> test();

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSMS(@Valid @RequestBody SMS sms);

    @PostMapping("/OTP")
    public ResponseEntity<SendVerificationCodeResponse> verifyIdentity(@Valid @RequestBody SendVerificationCodeRequest request);

}



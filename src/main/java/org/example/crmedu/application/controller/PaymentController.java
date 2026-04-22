package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.payment.DepositRequest;
import org.example.crmedu.application.dto.response.payment.DepositResponse;
import org.example.crmedu.application.dto.response.payment.GetBalanceResponse;
import org.example.crmedu.application.dto.response.payment.GetPaymentResponse;
import org.example.crmedu.application.mapping.PaymentDTOMapper;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.example.crmedu.domain.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payments")
public class PaymentController {

  private final PaymentService paymentService;
  private final PaymentDTOMapper paymentDTOMapper;
  private final JwtService jwtService;

  @GetMapping("/balance")
  @Secured({"STUDENT"})
  public ResponseEntity<GetBalanceResponse> getBalance() {
    var userId = jwtService.getCurrentUser().getId();
    var student = paymentService.getStudentWithBalance(userId);
    var response = new GetBalanceResponse()
        .setStudentId(student.getId())
        .setBalance(student.getBalance())
        .setHasDebt(student.getBalance() < 0);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/history")
  @Secured({"STUDENT"})
  public ResponseEntity<List<GetPaymentResponse>> getPaymentHistory() {
    var userId = jwtService.getCurrentUser().getId();
    var payments = paymentService.getPaymentHistory(userId);
    return ResponseEntity.ok(paymentDTOMapper.paymentsToGetResponses(payments));
  }

  @PostMapping("/deposit")
  @Secured({"STUDENT"})
  public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) {
    var userId = jwtService.getCurrentUser().getId();
    var newBalance = paymentService.deposit(userId, request.getAmount(), request.getDescription());
    return ResponseEntity.ok(new DepositResponse().setBalance(newBalance));
  }
}
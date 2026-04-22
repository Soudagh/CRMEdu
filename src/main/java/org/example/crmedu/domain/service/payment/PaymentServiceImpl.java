package org.example.crmedu.domain.service.payment;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.enums.PaymentStatus;
import org.example.crmedu.domain.enums.PaymentType;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.repository.PaymentRepository;
import org.example.crmedu.domain.service.student.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final StudentService studentService;

  @Override
  @Transactional
  public Student getStudentWithBalance(Long userId) {
    return studentService.getStudentByUserId(userId);
  }

  @Override
  @Transactional
  public List<Payment> getPaymentHistory(Long userId) {
    var student = studentService.getStudentByUserId(userId);
    return paymentRepository.findByStudentId(student.getId());
  }

  @Override
  @Transactional
  public Integer deposit(Long userId, Integer amount, String description) {
    var student = studentService.getStudentByUserId(userId);
    var payment = new Payment()
        .setStudent(student)
        .setAmount(amount)
        .setDescription(description)
        .setPaymentType(PaymentType.TOPUP)
        .setPaymentDate(ZonedDateTime.now())
        .setStatus(PaymentStatus.PAID);
    paymentRepository.create(payment);
    var newBalance = student.getBalance() + amount;
    student.setBalance(newBalance);
    studentService.update(student, student.getId());
    return newBalance;
  }
}
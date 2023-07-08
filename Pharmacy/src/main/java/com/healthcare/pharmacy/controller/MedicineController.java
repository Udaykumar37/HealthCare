package com.healthcare.pharmacy.controller;

import com.healthcare.pharmacy.exception.NoSuchMedicineException;
import com.healthcare.pharmacy.repository.entity.Medicine;
import com.healthcare.pharmacy.service.MedicineService;
import com.healthcare.pharmacy.util.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("medicine")
@AllArgsConstructor
public class MedicineController {

  private final MedicineService medicineService;

  @GetMapping
  public ResponseEntity<?> getAllMedicine() {
    log.info("Entered getAllMedicine");
    List<Medicine> medicineList = medicineService.getAllMedicine();
    log.info("Exited getAllMedicine");
    return new ResponseEntity<>(medicineList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMedicineById(@PathVariable("id") long id) {
    log.info("Entered getMedicineById={}", id);
    Optional<Medicine> medicineOptional = medicineService.getMedicineById(id);
    log.info("Exited getMedicineById={}", id);
    if (medicineOptional.isPresent()) {
      return new ResponseEntity(medicineOptional.get(), HttpStatus.OK);
    }
    throw new NoSuchMedicineException("Medicine Details Not Found for Id=" + id);
  }

  @GetMapping("/check/{id}")
  public ResponseEntity<?> checkMedicineById(@PathVariable Long id) {
    log.info("Entered checkMedicineById={}", id);
    Optional<Medicine> medicineOptional = medicineService.getMedicineById(id);
    log.info("Exited checkMedicineById={}", id);
    return new ResponseEntity<>(medicineOptional.isPresent(), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<?> getMedicineByName(@PathVariable("name") String name) {
    log.info("Entered getMedicineByName={}", name);
    List<Medicine> medicineList = medicineService.getMedicineByName(name);
    log.info("Exited getMedicineByName={}", name);
    return new ResponseEntity<>(medicineList, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> addMedicine(@RequestBody Medicine medicineRequest) {
    log.info("Entered addMedicine");
    Medicine medicineResponse = medicineService.addMedicine(medicineRequest);
    log.info("Exited addMedicine");
    return new ResponseEntity<>(medicineResponse, HttpStatus.CREATED);
  }

}

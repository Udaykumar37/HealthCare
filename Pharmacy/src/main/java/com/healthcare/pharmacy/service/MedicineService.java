package com.healthcare.pharmacy.service;

import com.healthcare.pharmacy.repository.MedicineRepository;
import com.healthcare.pharmacy.repository.entity.Medicine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MedicineService {

  private final MedicineRepository medicineRepository;

  public List<Medicine> getAllMedicine() {
    log.info("Entered getAllMedicine");
    return medicineRepository.findAll();
  }

  public Medicine addMedicine(Medicine medicineRequest) {
    log.info("Entered addMedicine");
    return medicineRepository.saveAndFlush(medicineRequest);
  }

  public Optional<Medicine> getMedicineById(long id) {
    log.info("Entered getMedicineById={}", id);
    return medicineRepository.findById(id);
  }

  public List<Medicine> getMedicineByName(String name) {
    log.info("Entered getMedicineByName={}", name);
    return medicineRepository.findByName(name);
  }
}

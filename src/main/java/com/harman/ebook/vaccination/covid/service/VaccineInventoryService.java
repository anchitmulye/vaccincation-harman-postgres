package com.harman.ebook.vaccination.covid.service;

import com.harman.ebook.vaccination.covid.constants.VaccinationConstants;
import com.harman.ebook.vaccination.covid.domain.Schedule;
import com.harman.ebook.vaccination.covid.domain.VaccineInventorySchedule;
import com.harman.ebook.vaccination.covid.entity.VaccineInventory;
import com.harman.ebook.vaccination.covid.repository.VaccineInventoryRepository;
import com.harman.ebook.vaccination.covid.response.ApplicationResponseService;
import com.harman.ebook.vaccination.covid.response.GenericResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.harman.ebook.vaccination.covid.constants.VaccinationConstants.DATE_FORMAT;

@Service
public class VaccineInventoryService {

    @Autowired
    private ApplicationResponseService appResponseService;

    @Autowired
    private VaccineInventoryRepository vaccineInventoryRepository;

    public GenericResponseEntity insertIntoVaccineInventory(VaccineInventorySchedule vacInvSchedule) throws ParseException {
        VaccineInventory inventory = new VaccineInventory();
        inventory.setLocation(vacInvSchedule.getLocation());
        inventory.setVacType(vacInvSchedule.getVacType());
        for(int i=0; i<vacInvSchedule.getSchedule().size(); i++) {
            Schedule schedule = vacInvSchedule.getSchedule().get(i);
            inventory.setDateOfAvailability(new SimpleDateFormat(DATE_FORMAT).parse(schedule.getDate()));
            inventory.setNoOfDoses(schedule.getNoOfDoses());
            vaccineInventoryRepository.save(inventory);
        }
        return appResponseService.genSuccessResponse(VaccinationConstants.SAVED_RECORDS, vacInvSchedule.getSchedule().size());
    }
}
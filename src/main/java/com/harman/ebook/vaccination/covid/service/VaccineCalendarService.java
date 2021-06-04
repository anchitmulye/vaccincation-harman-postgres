package com.harman.ebook.vaccination.covid.service;

import com.harman.ebook.vaccination.covid.constants.VaccinationConstants;
import com.harman.ebook.vaccination.covid.domain.VaccineCalendarVO;
import com.harman.ebook.vaccination.covid.entity.VaccineInventory;
import com.harman.ebook.vaccination.covid.repository.VaccineInventoryRepository;
import com.harman.ebook.vaccination.covid.response.ApplicationResponseService;
import com.harman.ebook.vaccination.covid.response.GenericResponseEntity;
import com.harman.ebook.vaccination.covid.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.harman.ebook.vaccination.covid.constants.VaccinationConstants.NEXT_DAYS;

@Service
public class VaccineCalendarService {

    @Autowired
    private ApplicationResponseService appResponseService;

    @Autowired
    private VaccineInventoryRepository vaccineInventoryRepository;

    /**
     *
     * @param fromDateString
     * @param location
     * @return list of vaccine inventories available for the next 5 days starting from fromDateString
     * @throws ParseException
     */
    public GenericResponseEntity getVaccineCalendarVO(String fromDateString, Short location) throws ParseException {
        Date fromDate = DateUtil.getDate(fromDateString);
        Date tillDate = DateUtil.getNextDate(fromDate, NEXT_DAYS);
        List<VaccineInventory> vaccineInventoryList = vaccineInventoryRepository.findByLocationAndDateOfAvailabilityBetweenAndIsActive(location, fromDate, tillDate, true);
        return appResponseService.genSuccessResponse(VaccinationConstants.RECORD_FOUNDS, getVaccineCalendarVO(vaccineInventoryList));
    }

    private List<VaccineCalendarVO> getVaccineCalendarVO(List<VaccineInventory> vaccineInventoryList) {
        List<VaccineCalendarVO> voList = new ArrayList<>();
        for (VaccineInventory vacInv: vaccineInventoryList) {
            VaccineCalendarVO vaccineCalendarVO = new VaccineCalendarVO();
            vaccineCalendarVO.setVacType(vacInv.getVacType());
            vaccineCalendarVO.setLocation(vacInv.getLocation());
            vaccineCalendarVO.setNoOfDoses(vacInv.getTotalNoOfDoses());
            vaccineCalendarVO.setDateOfAvailability(DateUtil.getDateString(vacInv.getDateOfAvailability()));
            vaccineCalendarVO.setNoOfDoses(vacInv.getTotalNoOfDoses());
            voList.add(vaccineCalendarVO);
        };
        return voList;
    }
}

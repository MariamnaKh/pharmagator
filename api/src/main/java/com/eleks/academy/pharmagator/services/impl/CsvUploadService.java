package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.constants.ErrorMessage;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.exceptions.FileUploadException;
import com.eleks.academy.pharmagator.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvUploadService {

    private final PersistenceService persistenceService;

    public void save(MultipartFile multipartFile) {
        try {
            List<MedicineDto> list = CsvUtil.parseBeans(multipartFile.getInputStream());
            list.forEach(persistenceService::storeToDB);
        } catch (IOException e) {
            throw new FileUploadException(ErrorMessage.ACCESS_ERROR);
        }
    }

}

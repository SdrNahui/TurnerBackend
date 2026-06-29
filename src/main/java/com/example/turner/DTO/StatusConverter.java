/*package com.example.turner.DTO;

import com.example.turner.Model.AppointmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<AppointmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatus status) {
        if(status == null){
            return null;
        }
        return status.name();
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        if(dbData.equals("CANCELLED")){
            return AppointmentStatus.CANCELED;
        }
        return AppointmentStatus.valueOf(dbData);
    }
}*/

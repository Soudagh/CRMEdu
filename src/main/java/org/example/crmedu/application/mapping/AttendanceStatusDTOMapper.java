package org.example.crmedu.application.mapping;

import java.util.List;
import org.example.crmedu.application.dto.response.user.GetAttendanceResponse;
import org.example.crmedu.domain.model.AttendanceStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceStatusDTOMapper {

  List<GetAttendanceResponse> domainListToDto(List<AttendanceStatus> attendanceStatuses);
}

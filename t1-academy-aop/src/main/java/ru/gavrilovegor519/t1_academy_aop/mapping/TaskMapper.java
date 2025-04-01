package ru.gavrilovegor519.t1_academy_aop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.gavrilovegor519.t1_academy_aop.dto.TaskDto;
import ru.gavrilovegor519.t1_academy_aop.entity.Task;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    List<Task> toEntity(List<TaskDto> taskDto);

    List<TaskDto> toDto(List<Task> taskDto);

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);
}
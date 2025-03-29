package ru.gavrilovegor519.t1_academy_aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.gavrilovegor519.t1_academy_aop.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

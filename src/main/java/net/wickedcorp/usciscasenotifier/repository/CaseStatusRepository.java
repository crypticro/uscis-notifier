package net.wickedcorp.usciscasenotifier.repository;

import net.wickedcorp.usciscasenotifier.model.CaseStatusTransformed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseStatusRepository extends JpaRepository<CaseStatusTransformed, String> {
}

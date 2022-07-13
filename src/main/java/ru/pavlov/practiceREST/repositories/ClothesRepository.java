package ru.pavlov.practiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavlov.practiceREST.models.Cloth;

@Repository
public interface ClothesRepository extends JpaRepository<Cloth, Integer> {
}

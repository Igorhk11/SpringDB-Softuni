package com.example.softunigamestore.repositories;

import com.example.softunigamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}

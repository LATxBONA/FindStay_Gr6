package com.example.PHONGTROSPRING.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PHONGTROSPRING.entities.Favorites;

@Repository
public interface FavoritePageRepository extends JpaRepository<Favorites, Integer>{

}

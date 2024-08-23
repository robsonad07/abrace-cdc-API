package com.abracecdcAPI.abracecdcAPI.domain.donation_action.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;

public interface DonationActionRepository extends JpaRepository<DonationActionEntity, UUID>{


}

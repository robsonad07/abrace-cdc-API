package com.abracecdcAPI.abracecdcAPI.domain.donation_event.repository;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DonationEventRepository extends JpaRepository<DonationEvent, UUID> {
}

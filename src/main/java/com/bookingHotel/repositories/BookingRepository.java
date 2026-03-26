package com.bookingHotel.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookingHotel.repositories.customs.BookingRepositoryCustom;
import com.bookingHotel.repositories.entities.BookingEntity;
import com.bookingHotel.repositories.enums.BookingStatus;

public interface BookingRepository
                extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity>,
                BookingRepositoryCustom {

        @Query("""
                        select (count(b) > 0) from BookingEntity b
                        where b.room.id = :roomId
                            and b.status in :statuses
                            and b.checkIn < :checkOut
                            and b.checkOut > :checkIn
                        """)
        boolean existsConflictingBooking(
                        @Param("roomId") Long roomId,
                        @Param("checkIn") LocalDate checkIn,
                        @Param("checkOut") LocalDate checkOut,
                        @Param("statuses") List<BookingStatus> statuses);

        @Query("""
                        select (count(b) > 0) from BookingEntity b
                        where b.room.id = :roomId
                            and b.id <> :bookingId
                            and b.status in :statuses
                            and b.checkIn < :checkOut
                            and b.checkOut > :checkIn
                        """)
        boolean existsConflictingBookingExcludingId(
                        @Param("roomId") Long roomId,
                        @Param("bookingId") Long bookingId,
                        @Param("checkIn") LocalDate checkIn,
                        @Param("checkOut") LocalDate checkOut,
                        @Param("statuses") List<BookingStatus> statuses);

        boolean existsByUserIdAndRoomIdAndStatus(Long userId, Long roomId, BookingStatus status);

}

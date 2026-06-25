package com.room_booking.room_service.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.room_booking.room_service.model.entity.Room;
import com.room_booking.room_service.model.enums.RoomStatus;

public record RoomResponse(
    long id,
    String roomId,
    String roomType,
    int capacity,
    BigDecimal pricePerNight,
    String description,
    String amenities,
    RoomStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
            room.getId(),
            room.getRoomId(),
            room.getRoomType(),
            room.getCapacity(),
            room.getPricePerNight(),
            room.getDescription(),
            room.getAmenities(),
            room.getStatus(),
            room.getCreatedAt(),
            room.getUpdatedAt()
        );
    }
}

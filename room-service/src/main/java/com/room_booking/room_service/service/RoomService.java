package com.room_booking.room_service.service;

import org.springframework.stereotype.Service;

import com.room_booking.room_service.mapper.RoomMapper;
import com.room_booking.room_service.model.dto.request.RoomRequest;
import com.room_booking.room_service.model.dto.response.RoomResponse;
import com.room_booking.room_service.model.entity.Room;
import com.room_booking.room_service.model.enums.RoomStatus;

@Service
public class RoomService {

    private final RoomMapper roomMapper;

    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public RoomResponse createRoom(RoomRequest request) {

        Room room = new Room();

        room.setRoomId(request.getRoomId());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        room.setDescription(request.getDescription());
        room.setAmenities(request.getAmenities());

        room.setStatus(RoomStatus.AVAILABLE); // Set default status to AVAILABLE

        roomMapper.insert(room);

        Room savedRoom = roomMapper.findByRoomId(room.getRoomId());

        return RoomResponse.from(savedRoom);
    }

    public RoomResponse getRoomById(String roomId) {

        Room room = roomMapper.findByRoomId(roomId);

        if (room == null) {
            throw new IllegalArgumentException(
                    "Room not found: " + roomId);
        }

        return RoomResponse.from(room);
    }

    public void updateRoomStatus(String roomId, RoomStatus status) {

        Room room = roomMapper.findByRoomId(roomId);

        if (room == null) {
            throw new IllegalArgumentException(
                    "Room not found: " + roomId);
        }

        roomMapper.updateRoomStatus(roomId, status);
    }
}
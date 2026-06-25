package com.room_booking.room_service.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.room_booking.room_service.model.entity.Room;
import com.room_booking.room_service.model.enums.RoomStatus;

@Mapper
public interface RoomMapper {
    Room findByRoomId(String roomId);
    void insert(Room room);
    void updateRoomStatus(
        @Param("roomId") String roomId, 
        @Param("status") RoomStatus status);
}

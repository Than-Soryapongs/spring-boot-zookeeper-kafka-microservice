CREATE TABLE IF NOT EXISTS rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    room_id VARCHAR(100) NOT NULL UNIQUE,
    room_type VARCHAR(50) NOT NULL,

    capacity INT NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,

    description TEXT,
    amenities TEXT,

    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_room_id (room_id),
    INDEX idx_room_type (room_type),
    INDEX idx_status (status)
);
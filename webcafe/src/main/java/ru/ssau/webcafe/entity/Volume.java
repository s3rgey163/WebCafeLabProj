package ru.ssau.webcafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

public record Volume(VolumeType type, int value) {
}
package com.tyh.aaron.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskByTaskIdReturn<E> {
    E taskData;
}

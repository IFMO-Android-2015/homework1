package com.example.pav.homework1.save;

import java.util.Queue;

/**
 * Контейнер для хранения данных при перевороте экрана.
 */
public class Container {

    public float memory;
    public boolean dot_control;
    public String stack_info, active_text, stack_text;
    public Queue<Float> numbers;
    public Queue<Character> operations;

    public Container (float memory, boolean dot_control, String stack_info, Queue<Float> numbers,
                      Queue<Character> operations, String active_text, String stack_text) {
        this.memory = memory;
        this.dot_control = dot_control;
        this.stack_info = stack_info;
        this.numbers = numbers;
        this.operations = operations;
        this.active_text = active_text;
        this.stack_text = stack_text;
    }
}

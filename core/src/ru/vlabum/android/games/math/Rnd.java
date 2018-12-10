package ru.vlabum.android.games.math;

import java.util.Random;

/**
 * Генератор случайных чисел
 */
public class Rnd {

    private static final Random random = new Random();

    /**
     * Сгенерировать случайное число
     * @param min минимальное значение случайного числа
     * @param max максимальное значение случайного числа
     * @return результат
     */
    public static float nextFloat(final float min, final float max) {
        return random.nextFloat() * (max - min) + min;
    }

    /**
     * Сгенерировать случайное целое число
     * @param bound предел значения
     * @return результат
     */
    public static int nextInt(final int bound) {
        return random.nextInt(bound);
    }

}

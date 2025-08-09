package org.zapphyre.function.monad;

import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.zapphyre.function.monad.Lens.l;

public class LensTest {

    @Test
    void testLens() {
        Car car = new Car(new Engine(new Part("part")));

        String result = l(car)
                .l(Car::getEngine)
                .l(Engine::getPart)
                .r(Part::getName);

        Assertions.assertEquals("part", result);
    }

    @Value
    class Car {
        Engine engine;
    }

    @Value
    class Engine {
        Part part;
    }

    @Value
    class Part {
        String name;
    }
}

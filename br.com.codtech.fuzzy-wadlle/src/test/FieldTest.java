package test;

import esception.ExceptionExplosed;
import model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FieldTest {
    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(3, 3);
    }

    @Test
    void tesNeighborsWithRealDistance() {
        Field neighbord = new Field(3, 2);
        boolean result = field.addNeigbor(neighbord);
        assertTrue(result);
    }

    @Test
    void tesNeighborsWithRealDistanceRigth() {
        Field neighbord = new Field(3, 4);
        boolean result = field.addNeigbor(neighbord);
        assertTrue(result);
    }

    @Test
    void testNeighborsWithNoNeighbord() {
        Field neighbord = new Field(1, 1);
        boolean result = field.addNeigbor(neighbord);
        assertFalse(result);
    }

    @Test
    void testAlterMarkedIsTrue() {
        field.alternatMarked();
        assertTrue(field.isMarked());
    }

    @Test
    void testAlterMarkedIsFalse() {
        field.alternatMarked();
        field.alternatMarked();
        assertFalse(field.isMarked());
    }

    @Test
    void testOpenNoMineFieldNoMarked() {
        assertTrue(field.open());
    }

    @Test
    void testOpenNoMineFieldMarked() {
        field.alternatMarked();
        assertFalse(field.open());
    }

    @Test
    void testOpenMineFieldMarked() {
        field.alternatMarked();
        field.open();
        assertFalse(field.open());
    }

    @Test
    void testOpenMineFieldAndNoMarked() {
        field.toMine();
        assertThrows(ExceptionExplosed.class, () -> {
            field.open();
        });
    }

}

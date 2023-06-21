package test;

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
        assertThrows(Exception.class, () -> {
            field.open();
        });
    }

    @Test
    void testOpenWithNeighbord1() {
        Field neighbordOf1 = new Field(1, 1);
        Field neighbord1 = new Field(2, 2);
        neighbord1.addNeigbor(neighbordOf1);
        field.open();
        assertTrue(neighbordOf1.isOpen() && neighbord1.isOpen());
    }

    @Test
    void testOpenWithNeighbord3() {
        Field neighbordOf1 = new Field(2, 2);
        Field neighbord1 = new Field(2, 2);
        neighbord1.addNeigbor(neighbordOf1);
        field.open();
        assertFalse(neighbordOf1.isOpen() && neighbord1.isOpen());
    }

    @Test
    void testOpenWithNeighbord2() {
        Field neighbord11 = new Field(1, 1);
        Field neighbord12 = new Field(1, 1);
        neighbord12.toMine();

        Field neighbord22 = new Field(2, 2);
        neighbord22.addNeigbor(neighbord11);
        neighbord22.addNeigbor(neighbord12);

        field.addNeigbor(neighbord22);
        field.open();
        assertTrue(neighbord22.isOpen() && neighbord11.isClosed());
    }
    // Creat test for Board
}

package com.itemis.challenge.constants;

public enum RomanNumberEnum {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    final int value;

    RomanNumberEnum(int value) {
        this.value = value;
    }

    /**
     *
     * @param romanNumber
     * @return the decimal value for the roman character, or -1 otherwise
     */
    public static int getDecimalValue(char romanNumber) {
        for (RomanNumberEnum n : RomanNumberEnum.values()) {
            if (n.name().charAt(0) == romanNumber) {
                return n.value;
            }
        }

        return -1;
    }
}

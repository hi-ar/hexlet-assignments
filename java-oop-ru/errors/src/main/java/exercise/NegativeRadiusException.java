package exercise;

import exercise.exception.CustomException;

// BEGIN
class NegativeRadiusException extends Exception {
    public static final CustomException NEGATIVE_RADIUS = new CustomException("NR", "Negative radius");

    private String wrongRadius;
    public NegativeRadiusException (String wrongRadius) {
        this.wrongRadius = wrongRadius;
    }
    public String getErrorDescription () {
        return "Radius might be positive, but was " + wrongRadius;
    }
}
// END

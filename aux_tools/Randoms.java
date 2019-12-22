package aux_tools;

public class Randoms {
    public static long getNotFractionalNumber(long min, long max) {
        return (long)(Math.random() * (++max - min) + min);
    }

    public static long[] getNotFractionalNumbers(long min, long max, int length) {
        long[] result = new long[length];
        for (int i = 0; i < length; i++) {
            result[i] = getNotFractionalNumber(min, max);
        }

        return result;
    }

    public static double getFractionalNumber(double min, double max, int precision) {
        double factor = Math.pow(10, (double)precision);
        double value = Math.random() * (++max - min) + min;

        return Math.round(value * factor) / factor;
    }

    public static double getFractionalNumber(double min, double max) {
        return getFractionalNumber(min, max, 2);
    }

    public static double[] getFractionalNumbers(double min, double max, int length, int...precision) {
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = (precision.length > 0) ? getFractionalNumber(min, max, precision[0]) :
                                                 getFractionalNumber(min, max);
        }

        return result;
    }

    public static char getCharacter(long minCodeValue, long maxCodeValue) {
        return (char)getNotFractionalNumber(minCodeValue, maxCodeValue);
    }

    public static char getCharacter(String str) {
        return str.charAt((int)getNotFractionalNumber(0, str.length() - 1));
    }

    public static boolean getBoolean() {
        return (getNotFractionalNumber(0, 1) == 1);
    }

    public static String getString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (getBoolean()) ? getCharacter(0x41, 0x5A) : getCharacter(0x61, 0x71);
        }

        return result;
    }

    public static String[] getStrings(int minStrLen, int maxStrLen, int length) {
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = getString((int)getNotFractionalNumber(minStrLen, maxStrLen));
        }

        return result;
    }
}


public class StringsUtils {
    public static String substringData(String data, String start, String end) {
        int startIndex = data.indexOf(start) + start.length();
        int endIndex = data.indexOf(end, startIndex);
        return data.substring(startIndex, endIndex);
    }
}

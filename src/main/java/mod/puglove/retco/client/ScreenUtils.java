package mod.puglove.retco.client;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ScreenUtils {
  private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
  static {
    suffixes.put(1_000L, "k");
    suffixes.put(1_000_000L, "M");
    suffixes.put(1_000_000_000L, "B");
    suffixes.put(1_000_000_000_000L, "T");
    suffixes.put(1_000_000_000_000_000L, "P");
    suffixes.put(1_000_000_000_000_000_000L, "E");
  }

  public static String getReadableNum(long value) {
    // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
    if (value == Long.MIN_VALUE)
      return getReadableNum(Long.MIN_VALUE + 1);
    if (value < 0)
      return "-" + getReadableNum(-value);
    if (value < 1000)
      return Long.toString(value); // deal with easy case

    Entry<Long, String> e = suffixes.floorEntry(value);
    Long divideBy = e.getKey();
    String suffix = e.getValue();

    long truncated = value / (divideBy / 10); // the number part of the output times 10
    boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
    return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
  }
}
import java.io.FileReader;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Problem {
    public static int decode(int base, String val) {
        return Integer.parseInt(val, base);
    }

    public static double interpolate(List<double[]> pts) {
        int n = pts.size();
        double constant = 0;
        for (int i = 0; i < n; i++) {
            double xi = pts.get(i)[0];
            double yi = pts.get(i)[1];
            double li = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double xj = pts.get(j)[0];
                    li *= (0 - xj) / (xi - xj);
                }
            }
            constant += yi * li;
        }
        return constant;
    }
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject input = (JSONObject) parser.parse(new FileReader("input.json"));
            JSONObject keys = (JSONObject) input.get("keys");
            long n = (long) keys.get("n");
            long k = (long) keys.get("k");
            List<double[]> pts = new ArrayList<>();
            for (Object key : input.keySet()) {
                String kStr = (String) key;
                if (kStr.equals("keys")) continue; // skip "keys" object
                long x = Long.parseLong(kStr);
                JSONObject yObj = (JSONObject) input.get(kStr);
                int base = Integer.parseInt((String) yObj.get("base"));
                String val = (String) yObj.get("value");
                int y = decode(base, val);
                pts.add(new double[]{x, y});
            }
            double c = interpolate(pts);
            System.out.println("Constant (c) is " + Math.round(c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Read {
    /**
     * Returns IP and Mask as square array
     * @param path String
     * @return int[][]
     */
    public static int[][] readIpAndMask(String path) {
        int[][] arr = new int[2][4];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String ip = reader.readLine();
            int i = 0;
            for (String s : ip.split("\\.")) {
                arr[0][i++] = Integer.parseInt(s);
            }
            String mask = reader.readLine();
            i = 0;
            for (String s : mask.split("\\.")) {
                arr[1][i++] = Integer.parseInt(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static List<Size> readSizes(String path) {
        List<Size> sizes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            String name;
            int size;
            while ((line = reader.readLine()) != null) {
                name = line.split("\\s+")[0];
                size = Integer.parseInt(line.split(("\\s+"))[1]);
                sizes.add(new Size(name, size));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sizes.stream().sorted(Size::compareTo).collect(Collectors.toList());
    }
}
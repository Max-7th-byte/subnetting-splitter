import java.util.*;

public class Main {

    private static final int[] real_arr = {2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768};

    public static void main(String[] args) {
        int[][] arr = Read.readIpAndMask("ex.txt");
        System.out.println("IP: " + Arrays.toString(arr[0]));
        System.out.println("Mask: " + Arrays.toString(arr[1]));
        int[] networkIP = getNetworkIP(arr[0], arr[1]);
        System.out.println("Network IP: " + Arrays.toString(networkIP));
        List<Size> sizes = Read.readSizes("sizes");
        List<Integer> realSizes = new ArrayList<>();
        System.out.println("Name\tSize\tReal size\tMask\t\tNetwork Ip\tHost From\tHost to\tBroadcast");
        for (Size size : sizes) {
            System.out.print(size.getName() + "\t\t");
            System.out.print(size.getSize() + "\t\t");
            int realSize = getRealSize(size.getSize());
            System.out.print(realSize + "\t\t\t");
            System.out.print(maskToString(getMask(realSize)) + "\t\t");
            realSizes.add(realSize);
            System.out.print(ipToString(networkIP) + "\t\t");
            int[] hostFrom = getHostFrom(networkIP);
            System.out.print(ipToString(hostFrom) + "\t\t");
            int[] broadcast = getBroadcast(realSize, networkIP);
            System.out.print(ipToString(getHostTo(broadcast)) + "\t\t");
            System.out.println(ipToString(broadcast) + "\t\t");
            networkIP = getNetworkIP(broadcast);
            System.out.println("\n");
        }
        System.out.println("Overall size = " + sumSizes(realSizes));
    }

    public static int[] getNetworkIP(int[] ip, int[] mask) {
        int[] new_arr = new int[4];
        for (int i = 0; i < 4; i++)
            new_arr[i] = ip[i] & mask[i];
        return new_arr;
    }

    public static int[] getNetworkIP(int[] broadcast) {
        int[] tmp = broadcast.clone();
        tmp[3] += 1;
        if (tmp[3] > 255) {
            tmp[2] += 1;
            tmp[3] = 0;
        }
        return tmp;
    }

    public static int[] getBroadcast(int realSize, int[] ip) {
        int[] tmp = ip.clone();
        int lastDigit = tmp[3];
        tmp[3] = 0;
        int tmpI = realSize + lastDigit;
        while (tmpI > 256) {
            tmp[2] = tmp[2] + 1;
            tmpI -= 256;
        }
        tmp[3] += tmpI - 1;
        return tmp;
    }

    public static int[] getHostFrom(int[] ip) {
        int[] tmp = ip.clone();
        tmp[3] += 1;
        if (tmp[3] > 255) {
            tmp[2] += 1;
            tmp[3] = 0;
        }
        return tmp;
    }

    public static int[] getHostTo(int[] ip) {
        int[] tmp = ip.clone();
        tmp[3] -= 1;
        return tmp;
    }


    public static int getRealSize(int req_size) {
        for (int i : real_arr) {
            if (i - req_size > 0)
                return i;
        }
        return 0;
    }

    public static int[] getMask(int real_size) {
        return switch (real_size) {
            case 2 -> new int[]{255, 255, 255, 254, 31};
            case 4 -> new int[]{255, 255, 255, 252, 30};
            case 8 -> new int[]{255, 255, 255, 248, 29};
            case 16 -> new int[]{255, 255, 255, 240, 28};
            case 32 -> new int[]{255, 255, 255, 224, 27};
            case 64 -> new int[]{255, 255, 255, 192, 26};
            case 128 -> new int[]{255, 255, 255, 128, 25};
            case 256 -> new int[]{255, 255, 255, 0, 24};
            case 512 -> new int[]{255, 255, 254, 0, 23};
            case 1024 -> new int[]{255, 255, 252, 0, 22};
            case 2048 -> new int[]{255, 255, 248, 0, 21};
            case 4096 -> new int[]{255, 255, 240, 0, 20};
            case 8192 -> new int[]{255, 255, 224, 0, 19};
            case 16384 -> new int[]{255, 255, 192, 0, 18};
            case 32768 -> new int[]{255, 255, 128, 0, 17};
            default -> null;
        };
    }

    public static String ipToString(int[] ip) {
        StringBuilder sb = new StringBuilder();
        for (int i : ip) {
            sb.append(i).append(".");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String maskToString(int[] ip) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ip.length; i++) {
            sb.append(ip[i]);
            if (i < ip.length - 2) {
                sb.append(".");
            }

            if (i == ip.length - 2) {
                sb.append("\\");
            }
        }
        return sb.toString();
    }

    public static int sumSizes(List<Integer> sizes) {
        return sizes.stream().reduce(0, Integer::sum);
    }
}
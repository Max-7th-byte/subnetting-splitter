public class Size implements Comparable<Size> {
    String name;
    int size;
    private static final boolean ATOZ = true;

    @Override
    public int compareTo(Size o) {
        if (this.size > o.size) return -1;
        else if (this.size < o.size) return 1;
        if (ATOZ) {
            return name.compareTo(o.name);
        } else {
            return name.compareTo(o.name) * -1;
        }
    }

    public Size(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
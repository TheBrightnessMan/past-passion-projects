package me.Bright.items.backpack;

public enum BackpackSize {
    TINY(9),
    SMALL(18),
    MEDIUM(27),
    LARGE(36),
    HUGE(45);

    private int size;

    public int getSize() {
        return size;
    }

    BackpackSize(int size) {
        this.size = size;
    }
}

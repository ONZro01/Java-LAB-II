public abstract class Quest {
    protected String id;
    protected String title;
    protected int difficulty;
    protected String requiredItem; // [Feature 2] เพิ่มไอเท็มที่ต้องใช้

    public Quest(String id, String title, int difficulty, String requiredItem) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.requiredItem = requiredItem;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getRequiredItem() { return requiredItem; }

    public abstract int rewardPoints(int key);
    public abstract boolean canComplete(int energy, int logic, int luck);

    @Override
    public String toString() {
        String req = (requiredItem.equals("None")) ? "" : " [Requires: " + requiredItem + "]";
        return id + " | " + title + " | diff=" + difficulty + req;
    }
}
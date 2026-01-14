public class DailyQuest extends Quest {
    private String rewardItem; // [Feature 2] ไอเท็มรางวัลที่ได้จากการทำ quest
    
    public DailyQuest(String id, String title, int difficulty) {
        super(id, title, difficulty, "None");
        this.rewardItem = "None";
    }
    
    // [Feature 2] Constructor ที่รับ rewardItem
    public DailyQuest(String id, String title, int difficulty, String rewardItem) {
        super(id, title, difficulty, "None");
        this.rewardItem = rewardItem;
    }
    
    public String getRewardItem() {
        return rewardItem;
    }

    @Override
    public int rewardPoints(int key) {
        return (difficulty * 3 + key) % 25 + 5;
    }

    @Override
    public boolean canComplete(int energy, int logic, int luck) {
        return true; // [Feature 2] DailyQuest ไม่มีเงื่อนไข สามารถทำได้เสมอ 
    }
}
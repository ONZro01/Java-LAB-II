public class StoryQuest extends Quest {
    public StoryQuest(String id, String title, int difficulty, String requiredItem) {
        super(id, title, difficulty, requiredItem);
    }

    @Override
    public int rewardPoints(int key) {
        return (difficulty * 10 + key * 2) % 60 + 20;
    }

    @Override
    public boolean canComplete(int energy, int logic, int luck) {
        // [Feature 2] StoryQuest ต้องการไอเท็มและ status ที่กำหนดตามระดับความยาก
        if (difficulty == 1) return energy > 75;
        if (difficulty == 2) return logic > 60;
        if (difficulty == 3) return luck > 70;
        if (difficulty == 4) return (energy > 75 && logic > 60);
        if (difficulty == 5) return (logic > 60 && luck > 70);
        return false;
    }
}
import java.util.*;

public class QuestService {
    public ArrayList<Quest> quests = new ArrayList<>();
    public HashMap<String, Integer> stats = new HashMap<>();
    
    // [Feature 2] ระบบ Inventory
    public HashMap<String, Integer> inventory = new HashMap<>();

    public QuestService() {
        stats.put("completed", 0);
        stats.put("failed", 0);
        // เริ่มต้นด้วยกระเป๋าว่าง หรือให้ไอเท็มพื้นฐาน
        inventory.put("Data-Disk", 0);
        inventory.put("Code-Key", 0);
        inventory.put("Memory-Core", 0);
    }

    public void seedQuests(int key) {
        // DailyQuest แต่ละอันให้รางวัลไอเท็มต่างกัน
        quests.add(new DailyQuest("D-" + key + "-1", "Scan Lab Tools", (key % 5) + 1, "Data-Disk"));
        quests.add(new DailyQuest("D-" + key + "-2", "Clean Dataset", ((key + 1) % 5) + 1, "Code-Key"));
        quests.add(new DailyQuest("D-" + key + "-3", "Refactor Code", ((key + 2) % 5) + 1, "Memory-Core"));

        // StoryQuest แต่ละอันต้องการไอเท็มต่างกัน
        quests.add(new StoryQuest("S-" + key + "-1", "Decrypt Archive", ((key + 3) % 5) + 1, "Data-Disk"));
        quests.add(new StoryQuest("S-" + key + "-2", "Escape the Loop", ((key + 4) % 5) + 1, "Code-Key"));
        quests.add(new StoryQuest("S-" + key + "-3", "Boss: NullPointer", ((key + 5) % 5) + 1, "Memory-Core"));
    }

    public void listQuests() {
        for (Quest q : quests) System.out.println(q.toString());
    }

    // [Feature 1] Exception ถ้าหา ID ไม่เจอ
    public Quest findById(String id) throws InvalidQuestException {
        for (Quest q : quests) {
            if (q.getId().equalsIgnoreCase(id)) return q;
        }
        throw new InvalidQuestException("ไม่พบเควสต์ไอดี: " + id + " ในระบบ");
    }
}
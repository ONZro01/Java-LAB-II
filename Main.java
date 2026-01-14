import java.util.*;

// [Feature 1] Custom Exception สำหรับรหัสนักศึกษาไม่ถูก
class InvalidStudentIdException extends Exception {
    public InvalidStudentIdException(String message) {
        super(message);
    }
}

public class Main {

    static int sumDigits(String studentId) {
        int sum = 0;
        for (int i = 0; i < studentId.length(); i++) {
            char c = studentId.charAt(i);
            if (Character.isDigit(c)) sum += (c - '0');
        }
        return sum;
    }

    // [CP4] ฟังก์ชันสร้าง Signature 
    static String makeSignatureV2(String studentId, int score, int completed) {
        int checksum = 7;
        for (int i = 0; i < studentId.length(); i++) {
            checksum = (checksum * 37 + studentId.charAt(i)) % 100000;
        }
        checksum = (checksum + score * 89 + completed * 101) % 100000;
        return "SIG2-" + String.format("%05d", checksum);
    }

    // [CP4] Error Handling: ใช้ try-catch ป้องกันโปรแกรมล่มเมื่อกรอกค่าไม่ใช่ตัวเลข
    static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("ค่าที่กรอกไม่ถูกต้อง (" + min + "-" + max + ")");
        }
    }

    // [Feature 1] ฟังก์ชันตรวจสอบรหัสนักศึกษา
    static void validateStudentId(String id) throws InvalidStudentIdException {
        if (id.length() != 10 || !id.matches("\\d+")) {
            throw new InvalidStudentIdException("รหัสนักศึกษาต้องเป็นตัวเลข 10 หลักเท่านั้น!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuestService qs = new QuestService();
        String studentId = "";

        // [Feature 1] Loop จนกว่าจะกรอกรหัสถูก
        while (true) {
            try {
                System.out.print("กรอกรหัสนักศึกษา (10 หลัก): ");
                studentId = sc.nextLine().trim();
                validateStudentId(studentId);
                break;
            } catch (InvalidStudentIdException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        int key = sumDigits(studentId) % 97;
        int energy = (key * 7 + 13) % 100;
        int logic  = (key * 11 + 5) % 100;
        int luck   = (key * 17 + 19) % 100;

        qs.seedQuests(key);

        int totalScore = 0;
        while (true) {
            System.out.println("\n[Menu] 1) List 2) Do quest 3) Inventory/Stats 4) Exit");
            System.out.println("[Status] Energy: " + energy + " | Logic: " + logic + " | Luck: " + luck);
            int m = askInt(sc, "เลือก: ", 1, 4);

            if (m == 1) {
                qs.listQuests();

            } else if (m == 2) {
                System.out.print("พิมพ์ quest id: ");
                String id = sc.nextLine().trim();

                try {
                    Quest q = qs.findById(id); // InvalidQuestException

                    // [Feature 2] เช็คไอเท็ม
                    if (!q.getRequiredItem().equals("None")) {
                        int count = qs.inventory.getOrDefault(q.getRequiredItem(), 0);
                        if (count <= 0) {
                            System.out.println("ทำไม่ได้! คุณไม่มีไอเท็ม: " + q.getRequiredItem());
                            continue;
                        }
                    }

                    if (q.canComplete(energy, logic, luck)) {
                        int r = q.rewardPoints(key);
                        totalScore += r;
                        qs.stats.put("completed", qs.stats.get("completed") + 1);
                        
                        // [Feature 2] ถ้าทำ DailyQuest สำเร็จ จะได้ไอเท็ม และเพิ่ม energy
                        if (q instanceof DailyQuest) {
                            DailyQuest dq = (DailyQuest) q;
                            String reward = dq.getRewardItem();
                            if (!reward.equals("None")) {
                                qs.inventory.put(reward, qs.inventory.get(reward) + 1);
                                System.out.println("ได้รับไอเท็ม: [" + reward + "] x1");
                            }
                            energy = Math.min(99, energy + 10); // เพิ่ม energy แต่ไม่เกิน 99
                            System.out.println("Energy +10");
                        }
                        
                        // [Feature 2] ถ้าทำ StoryQuest สำเร็จ จะเสียไอเท็ม และเพิ่ม logic + luck
                        if (q instanceof StoryQuest) {
                            String requiredItem = q.getRequiredItem();
                            qs.inventory.put(requiredItem, qs.inventory.get(requiredItem) - 1);
                            System.out.println("ใช้ไอเท็ม: [" + requiredItem + "] x1");
                            logic = Math.min(99, logic + 8);  // เพิ่ม logic แต่ไม่เกิน 99
                            luck = Math.min(99, luck + 8);    // เพิ่ม luck แต่ไม่เกิน 99
                            System.out.println("Logic +8, Luck +8");
                        }

                        System.out.println("สำเร็จ! +" + r + " points");
                    } else {
                        qs.stats.put("failed", qs.stats.get("failed") + 1);
                        System.out.println("Status ไม่เพียงพอ!");i
                    }
                } catch (InvalidQuestException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (m == 3) {
                System.out.println("--- Stats ---");
                System.out.println("Score: " + totalScore + " | Completed: " + qs.stats.get("completed"));
                System.out.println("--- Inventory ---");
                qs.inventory.forEach((k, v) -> System.out.println(k + ": " + v));

            } else {
                break;
            }
        }

        System.out.println("\n=== Result ===");
        System.out.println("score=" + totalScore);
        // [CP4] แสดง Signature ตอนจบ
        System.out.println("signature=" + makeSignatureV2(studentId, totalScore, qs.stats.get("completed")));

        // [CP4] ข้อสรุปเกี่ยวกับ OOP
        System.out.println("OOP ช่วยให้โค้ดดูแลง่ายขึ้นด้วยการแยก Logic ของเควสต์แต่ละประเภทออกจากกัน");
        System.out.println("ทำให้เมื่อต้องการเพิ่มเควสต์ชนิดใหม่ เราไม่จำเป็นต้องแก้ไขโค้ดหลักใน Main");

        sc.close();
    }
}

# Java-LAB-II: Quest System Upgrade

## Feature 1: ระบบ Custom Exception 

ใช้คลาส `InvalidStudentIdException` เพื่อควบคุมการกรอกข้อมูลของผู้ใช้

**[Feature 1] การตรวจสอบรหัสนักศึกษา**
- โปรแกรมจะบังคับให้กรอก **ตัวเลข 10 หลักเท่านั้น**
- หากกรอกตัวอักษรหรือความยาวไม่ครบ จะแสดง Error และให้กรอกใหม่จนกว่าจะถูกต้อง
- ใช้ `validateStudentId()` method ตรวจสอบเข้าด้วย regex

**[Feature 1] การตรวจสอบ Quest ID**
- เมื่อเลือกทำเควสต์ หากใส่ ID ที่ไม่มีอยู่ในระบบ โปรแกรมจะ throw `InvalidQuestException`

---

## Feature 2: ระบบไอเท็ม + Status Enhancement System

### 2.1 ระบบไอเท็ม (Item Inventory)

**[Feature 2] ไอเท็มในระบบ (3 ประเภท)**
- **Data-Disk** - ได้จาก D-xx-1 (Scan Lab Tools)
- **Code-Key** - ได้จาก D-xx-2 (Clean Dataset)  
- **Memory-Core** - ได้จาก D-xx-3 (Refactor Code)

**[Feature 2] Inventory Management**
- เก็บข้อมูลในรูปแบบ `HashMap<String, Integer>`
- DailyQuest ให้รางวัลไอเท็ม (คนละแบบ)
- StoryQuest ต้องการไอเท็ม (คนละแบบ)

### 2.2 Status Enhancement System

**[Feature 2] สถานะที่ติดตามจำนวน (3 ตัว)**
- **Energy** - เพิ่มจาก DailyQuest (+10 ต่อครั้ง, สูงสุด 99)
- **Logic** - เพิ่มจาก StoryQuest (+8 ต่อครั้ง, สูงสุด 99)
- **Luck** - เพิ่มจาก StoryQuest (+8 ต่อครั้ง, สูงสุด 99)

**[Feature 2] StoryQuest Complete Condition**
- difficulty 1: `energy > 75`
- difficulty 2: `logic > 60`
- difficulty 3: `luck > 70`
- difficulty 4: `energy > 75 && logic > 60`
- difficulty 5: `logic > 60 && luck > 70`

---

## Workflow Example

```
Step 1: กรอก 10-digit student ID
        └─ [Feature 1] ระบบตรวจสอบรหัส
        
Step 2: คำนวณ Status เริ่มต้น:
        - energy = (key * 7 + 13) % 100
        - logic = (key * 11 + 5) % 100
        - luck = (key * 17 + 19) % 100
        
Step 3: ทำ DailyQuest (เสร็จ):
        - [Feature 2] ได้ไอเท็มรางวัล 1 ชิ้น
        - [Feature 2] Energy: 45 → 55
        - Score: +12
        
Step 4: ทำ DailyQuest บ้างอื่น ๆ เพื่อให้ Status สูงขึ้น
        - Energy: 55 → 65 → 75+
        - Inventory: Code-Key 0 → 1, Memory-Core 0 → 1
        
Step 5: ตอนนี้สามารถทำ StoryQuest ได้ (มีไอเท็มและ Status พอ)
        - [Feature 2] ตรวจสอบไอเท็มและ Status
        - [Feature 2] Logic: 60 → 68, Luck: 70 → 78
        - [Feature 2] Inventory: Code-Key: 1 → 0 (ใช้ไป)
        - Score: +35
```

---

## How to Run

```bash
javac *.java
java Main
```


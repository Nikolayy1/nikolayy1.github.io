# CCL3 Project - Daily Quest
_by Niklas Hofstetter and Iosif Cicio_ <br>
[APK Download](https://mega.nz/file/NCkkjCoA#ns7Ho5f8RrfaY25T7CjvUPrs6t_uTOOjF8EnMVLwILs)
<br>
## Description
Daily Quest is a gamified task management app that transforms your daily responsibilities into an epic adventure. With features like a leveling system and a Quick Board for top-priority tasks, it keeps users motivated and productive. Inspired by RPG mechanics, Daily Quest allows users to focus on their goals while earning rewards for their accomplishments.
<br>
## App Concept
### Database
We used a Room database to store our Quests and Datastore to store the Experience points of the user, stats and avatar choice. 
<br>

### Use Case
Daily Quest is designed for individuals who want to stay productive while having fun. It provides a visual and interactive way to manage tasks by incorporating gamification elements like experience points, levels, and a task prioritization system. 
Users can:
-	Mark tasks as "Quick Board" for focused attention.
-	Earn XP and level up by completing tasks.
-	Allocate stat points (Endurance, Intelligence, Focus) as you progress.
This app helps users maintain productivity by creating a rewarding and enjoyable experience.
<br>

### Target User
Demographics: <br>
- Age Group: 15–35 years old.
- Tech-Savvy: Familiar with mobile apps and gaming concepts.
<br>

Motivations: <br>
- Stay organized and productive.
- Enjoy a gamified experience for task management.
<br>

Interests:<br>
- RPG games, personal growth, and productivity hacks.
<br>

Paint points:<br>
- Finds traditional to-do lists uninspiring.
- Struggles to stay motivated with mundane task management apps.
<br>

Possible User Groups:<br>
- Young Adults & College Students:
  - Struggling to balance studies, work, and maintaining a tidy living space.
  - Seeking motivation and structure to keep up with chores.


- Families with Kids
  - Parents looking for a fun way to engage children in household tasks.
  - A system to gamify chores and encourage responsibility in a playful manner.


- Professionals & Busy Individuals
  - Juggling work and personal life, often feeling overwhelmed by household duties.
  - Interested in efficient and rewarding tools to stay on top of chores.



### Wireframe
![Mock](https://github.com/user-attachments/assets/d0788712-b369-4aee-8644-49667770f582)

### User Flow
![User Flow](https://github.com/user-attachments/assets/efa58eeb-5bb2-4b59-be9c-e9ac6eccabea)

## Usability Test Plan
### Heuristic Evaluation
Using Nielsen's 10 we evaluated the current version of our app. Below are our findings:

#### 1. Visibility of System Status
- No clear indication that a quest got successfully created
- Search bar: No information provided to press on search button to complete the operation
- Filter: Not showing what filter is currently applied
- No confirmation that the edited quest got saved
- No Error Message when wanting to add more than 3 tasks to quick board

#### 2. User Control and Freedom
- Once marking a quest as completed, it's gone (no undo option)

#### 3. Error Prevention
- Users can leave the edited quest without saving
- Users could try to mark more than 3 tasks on the quick board

#### 4. Recognition Rather Than Recall
- Icons rely heavily on the user knowing what they do (like saving, deleting, ...)

#### 5. Aesthetic and Minimalist Design
- Beige color might feel less engaging to users (try new color schemes)

#### 6. Help and Documentation
- New users might not know the system of EXP/Leveling/Stats

### Hypothesis & Questions
#### Initial Hypothoses
Users who enjoy gamified systems will find the app more motivating compared to traditional task management apps.

#### Questions
Do users find this app more motivating than traditional management apps?<br>
How intuitive is the Quick Board for prioritizing tasks?<br>
Are users satisfied with the stat allocation process, and does it encourage continued use?<br>
How well do users understand the visual hierarchy of task categories (Low, Medium, High)?<br>

### Data Collection
#### Dependent Variables
- Task Completion Rate
- Time on Task: Average time spent completing tasks.
- Ease of use: SEQ after task

#### Methods
- SEQ
- Interview
- Microsoft Forms

Link to Forms: https://forms.office.com/e/1DtAKgYDsM

### Results
We tested 5 people, 3 in person and 2 online. The results were quite good and very helpful. These are the key results:<br>

<b>Strengths:</b>
<br>
- The app is user-friendly, with tasks rated highly (6-7) for ease of use.
- Minimalistic design and intuitive navigation were well-received.
- Gamification elements effectively motivated task completion and increased engagement.

<br>

<b>Challenges:</b>
<br>
- Some users found the Quickboard feature unclear.
- A desire for more features, such as quests, mini-games, and notifications, was expressed.

<br>

<b>Overall Sentiment:</b>
<br>
- Users found the app productive and engaging, with potential for regular use if improvements are implemented.

<br>

![SEQ Ratings](https://github.com/user-attachments/assets/16251d29-a769-4d4b-b640-d8b68b8a22df)
<br>
The chart shows the average SEQ Score per task.

## Final Reflection
### Improvements
After testing and presenting our app we decided to fix the occuring bugs and improve the app. In our initial concept there was no Avatar selection planned, so this is a feature we added at the end to make the app feel a bit more fun to use. However, at the final presentation we noticed that there was an issue with saving the selected Avatar after switching screens. We solved this by also storing the avatar using DataStore. <br>
There could be more things to improve, but the general concept and idea of our app was successfully implemeneted. Further features could be: <br>
- Making sure user cant turn the phone sideways, so the input value doesn't get reset
- Different color schemes for dark mode and light mode
- Enhance Quickboard usability with tutorials or tooltips
- Integrate notification system
- Expand gamification with additional interactive features
<br>

### Niklas
My main task in this project was to create and implement the UI. At the beginning I had some trouble figuring out the best way to implement and change colors and fonts but after some time it was quite easy. I had a very nice learning experience regarding UI, Kotlin and version control using github as a team. I think our initial concept was quite good, but we decided to change some things while developing, like the color scheme. Designing and performing the usability test was also a quite smooth process, since me and Iosif already worked together in UEE and knew how we should approach it. In the end I think this was a good project to further improve design, mobile development and user testing skills. 

<br>

### Iosif
First and foremost, I worked on developing the main screen of our project, which included the experience (EXP) system, quickboard feature, leveling mechanics, avatar selection, and stat allocation. I also worked closely with Niklas on refining UI interactions and debugging. Throughout the project, I reinforced my knowledge regarding version control, particularly working with GitHub in a team. Seeing our usability tests confirm that the gamification elements indeed increased the motivation of users was rewarding. Overall, this project helped me deepen my skills in Kotlin, Jetpack Compose, and UX testing while successfully bringing our concept to life. 



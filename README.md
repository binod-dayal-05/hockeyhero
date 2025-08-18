# Hockey Hero

## A game that simulates ice hockey seasons.

My personal project will be an ice hockey simulator where the user is able to edit their team's lineup with given players of different overall levels and **simulate** games against other teams in the league. Games can result in a win, tie, or loss. Results are based on *probability*, which will be based on the overall levels of the team's players. The **higher** the average overall a team has over another team, the more likely they are to win. Players will cost money and their is a set amount of money the user can spend, so it limits them from selecting all of the best players and instead try to balance their team. If the user exits the program, they will be able to resume where they were upon returning. The league will end after a certain amount of games have been played.

This program is intended for users who are interested in hockey, team-building, or just want to play a fun little game. If the user likes fantasy leagues for sports like hockey, they may enjoy playing this game. It requires strategically spending the available money to build a team that can win more games than it loses by the end of the league and finish first in the standings to win. This project is of interest to me because I enjoy watching ice hockey and playing games related to it. There aren't many popular hockey games available to play so I want to make this program because it isn't as popular of a game genre, and hockey deserves more recognition than it gets.

## User Stories

- As a user, I want to be able to create a new hockey team and give it a name.
- As a user, I want to be able to view all the hockey players available for me to select and view all the players I have already selected.
- As a user, I want to be able to select a hockey player to add to my team, or remove a previously selected hockey player from my team.
- As a user, I want to be able to simulate hockey games and have a higher chance of winning if my team's stats are better than my opponent's team stats.
- As a user, I want to be able to view my position in the league standings relative to the other teams in the league, along with the win/loss record of all teams.
- As a user, I want to be able to save where I was in the game which includes all data of my team, the other teams in the league, and league standings, schedules, win/loss records, and more.
- As a user, I want to be able to load a previously saved version of my game and resume from where I left off when I saved it.
- As a user, in the GUI, I want to be able to view a panel with all the Players in my Team listed out in the order they were added.
- As a user, in the GUI, when viewing my roster, I want to be able to remove a Player from my Team.
- As a user, in the GUI, when viewing my roster, I want to be able to click a button that displays the player with the highest average stats from all the Players in my Team.

## Instructions for Grader

- Note: for everything below, the Xs are of type Player and the Y is of type Team. 
- After setting up the team, you can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking purchase and purchase whichever players you want. Then click "View Roster" to see all the players added to the roster.
- After setting up the team and purchasing some players, you can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "View Roster" button and clicking the "Remove" tab next to a player, which will remove the chosen player from the roster.
- After setting up the team and purchasing some players, you can generate a third (if needed in case one of the previous 2 is not adequate) action related to the user story "adding multiple Xs to a Y" by clicking the "View Roster" button and clicking the "Show Best Player On Team" button on the right. This will filter through every Player in the Team, and display the info for the Player in the Team with the highest average stats.
- After setting up the team and purchasing at least 3 players, you can locate my visual component by clicking the "Play Games" button on the bottom right. Then, you can click the "Next Game" button that shows up which will display the stock ice hockey image from the data folder onto the screen.
- You can save the state of my application by clicking the "Save" button. This is not available if the user has not yet created their team.
- You can reload the state of my application by clicking the "Load" button. This is only available while the user has not yet created their team.

## Phase 4: Task 2

Mon Aug 05 16:03:55 PDT 2024
User team created: Vancouver Canucks
Mon Aug 05 16:04:06 PDT 2024
Player Alexander Lemieux added to user team's roster.
Mon Aug 05 16:04:08 PDT 2024
Player Quinn MacKinnon added to user team's roster.
Mon Aug 05 16:04:11 PDT 2024
Player Auston Bure added to user team's roster.
Mon Aug 05 16:04:16 PDT 2024
Player Alexander Lemieux removed from user team's roster.
Mon Aug 05 16:04:40 PDT 2024
Player Brock Ovechkin added to user team's roster.
Mon Aug 05 16:04:43 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:04:44 PDT 2024
User team 'Vancouver Canucks' tied a game.
Mon Aug 05 16:04:45 PDT 2024
User team 'Vancouver Canucks' won a game.
Mon Aug 05 16:04:46 PDT 2024
User team 'Vancouver Canucks' tied a game.
Mon Aug 05 16:04:47 PDT 2024
User team 'Vancouver Canucks' tied a game.
Mon Aug 05 16:04:48 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:04:52 PDT 2024
Player Quinn MacKinnon removed from user team's roster.
Mon Aug 05 16:04:54 PDT 2024
Player Brock Ovechkin removed from user team's roster.
Mon Aug 05 16:04:59 PDT 2024
Player Leon Lemieux added to user team's roster.
Mon Aug 05 16:05:07 PDT 2024
Player Bobby Draisaitl added to user team's roster.
Mon Aug 05 16:05:09 PDT 2024
Player Steven MacKinnon added to user team's roster.
Mon Aug 05 16:05:15 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:05:15 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:05:17 PDT 2024
Player Bobby Draisaitl removed from user team's roster.
Mon Aug 05 16:05:23 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:05:24 PDT 2024
User team 'Vancouver Canucks' lost a game.
Mon Aug 05 16:05:25 PDT 2024
User team 'Vancouver Canucks' won a game.

## Phase 4: Task 3

After reflecting upon my UML class diagram for my project, I think the way I designed my code was not an organized way of doing it, which led to the class diagram being very messy. There is a large amount of associations between classes and I think with more time I would have found a way to cut that number down. For example, one way I can already think of to improve the design of my code would be to have a supertype class for some of my ui classes to extend. HockeyHeroGUI.java has a lot of common functionality between Manage.java and Game.java. A lot of the code is similar, in fact the only real differences is the HockeyHeroGUI.java displays visual components on a graphical user interface whereas Manage.java and Game.java display text on the console, but they both represent the same information. 

What I would do is in the supertype, I would declare and implement the functions that are essentially the exact same such as viewing standings, simulating games, purchasing player and more. Then the three ui classes would call on these functions and before or after the call they can work out the slight variations they may have such as printing to the console or adding panels to a JFrame. This would reduce a lot of duplication in my code and make it more organized and easier to read. Another thing I could think of is using the Singleton design pattern. In my code, I often call methods with fields of classes I designed as parameters, because I need to make sure it's the same instance of that field. But if I made a global instance of the field such as a Market or League, I could achieve the same result without so many unnessecary parameters added. This would improve the overall design of my class by making it more cohesive. Overall these suggestions would make my code shorter, have less duplication, make it more cohesive, and easier to read and understand.
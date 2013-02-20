Chatter Reports

Created By: Harsha Balasubramanian
===============

Application Overview
--------------------
This web application reports the chatter posts submitted/mentioned by a logged-in user on a given date. The application 
is running at https://chatterreport.cloudfoundry.com/Report


Definition of a post
--------------------
A chatter post is assumed to be one of the following:
1. User post on user's wall
2. User posts on other's wall
3. User posts to groups
4. Comments (Shown in 'more details'. See below)
5. User mentions to others
6. User mentions recieved

Simply put, all posts in the logged-in user's profile page are candidates for display

Component Description
---------------------

User profile : The logged-in user's profile name is displayed at the top right corner along with the image of the user.

Ordering of Posts : A drop-down menu is used to present the dates on which the user submitted one or more posts. The 
dates are presented with the 'Most' Recent Post' at the beginning of the drop-down (and seelcted by default). 

Toggling between Dates : Selecting a different date will trigger a request to the server to get the posts belonging to 
that date.

Post Information : The following infomation are given for each post sumbited by the user
1. Date and time of submission
2. The post message (along with the total number of posts for that date)
3. The post type (Text, Mention)
4. The number of likes for that post
5. The number of comments recieved by that post
6. More Details - Shows the complete post in a popup window. THis section contains the comments recieved for posts 
and the comment posted by the user

Logout : Invalidates the user's session and performs a soft login. Clearing the browser cookies will
prompt the user to provide the login credentials.


Sample Users
------------
harshab85@gmail.com           qwerty123$ 

sharanyaharsha@gmail.com      qwerty123$ 

benkingtest@gmail.com         qwerty123$ 

maxpaynetest@gmail.com        qwerty123$ 

rogerbanner@gmail.com         qwerty123$

chattertestuser100@gmail.com  qwerty123$ (Email for confirmation: chattertestuser@gmail.com)


List of known issues
--------------------

1. Logout - The 'sid' cookie which needs to be removed for prompting the login page is not available in the request 
context. This can be resolved by channging the application context path to / instead of /Report. Clearing the browser 
cookies will alos prompt the user to login.

2. Profile picture - The user's profile picture is display only when the user is logged-in though the dev community 
page. This can be tested with harshab85@gmail.com/qwerty123$




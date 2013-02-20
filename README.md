Chatter Reports

Created By: Harsha Balasubramanian
===============

Application Overview
--------------------
This web application reports the chatter posts submitted by a logged-in user on a given date.


Definition of a post
--------------------
A chatter post is assumed to be one of the following:
1. User post/comment on user's wall
2. User posts/comment on other's wall
3. User posts/comment to groups
4. User mentions others

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
3. The post type (Text, Mention, Comment)
4. The number of likes for that post
5. The number of comments recieved by that post

Logout : Will redirect to login page






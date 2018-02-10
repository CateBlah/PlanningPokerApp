select userID, userName from users where BINARY email = 'thejokerwillalwayssmile@yahoo.com';

drop table newsfeed;

select userproject.userID, users.userName,userstories.userStoryName,projects.projectName from userproject
inner join userstories on userstories.projectId = userproject.projectId and userstories.userStoryId = 1
inner join users on users.userID = userproject.userID
inner join projects on projects.projectId = userproject.projectId;
update users SET userName = 'Caterina', userpassword='cate' where userID = 1;

create table newsfeedmessage(
  newsfeedMessageId int PRIMARY KEY AUTO_INCREMENT,
  newsfeedMessage varchar(140)
);


select * from userstories where projectId =  35;
select users.userName from users
INNER JOIn userproject on users.userID = userproject.userID and userproject.projectId = 35;
select users.userName, newsfeedMessage.newsfeedMessage from newsfeedmessage
INNER join usernewsfeed on newsfeedmessage.newsfeedMessageId = userNewsFeed.newsfeedMessageId
INNER join users on userNewsFeed.userID = users.userID and users.userName = 'Caterina';

insert into newsfeedmessage(newsfeedMessage) VALUE ('Another test');
insert into userNewsFeed(newsfeedMessageId, userID) VALUE (2,1);
create table userNewsFeed(
  newsfeedMessageId int not null,
  userID int not null,
  FOREIGN KEY (newsfeedMessageId) REFERENCES newsfeedmessage(newsfeedMessageId),
  FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE,
  PRIMARY KEY (newsfeedMessageId,userID)
);

drop table newsfeedmessage;
drop table userNewsFeed;
select * from userNewsFeed;
select * from newsfeedmessage;
delete from newsfeedmessage where newsfeedMessageId = 5;
delete from userNewsFeed where newsfeedMessageId = 5;
alter table newsfeedmessage
    AUTO_INCREMENT = 5;

select users.username from users
INNER join userproject on users.userID = userproject.userID and userproject.projectId = 7;
alter table userproject AUTO_INCREMENT = 11;
alter table userstories AUTO_INCREMENT = 1;
delete from userproject where userID = 14;
delete from userstories where userStoryId > 0;
select * from projects where projectName = 'BonoTest';
delete from userproject where projectId = 7 and userID != 3;
select * from userproject where userproject.projectId = 7;
select userName from users where userID = 3;

select u.userName,u.userID,u.email from users u inner join userproject up on u.userID = up.userID and projectId = 7 and u.userName != 'Bono';
select u.userName,u.userID,u.email from users u inner join userproject up on u.userID = up.userID and projectId = 7;
delete from userproject where userID = 14 and projectId = 7;

delete from userStories where userStoryId < 100;

alter table userstories
    add assigneeId int;
alter table userstories
    add constraint fk_assigneeId FOREIGN KEY (assigneeId) REFERENCES users(userID);

create table notificationmessage(
  notificationMessageId int PRIMARY KEY,
  notificationMessage varchar(140)
);

create table usernotification(
  userId int not null,
  notificationMessageId int not null,
  FOREIGN KEY (userId) REFERENCES users(userID) ON DELETE CASCADE ,
  FOREIGN KEY (notificationMessageId) REFERENCES notificationmessage(notificationMessageId) ON DELETE CASCADE,
  PRIMARY KEY(userId,notificationMessageId)
);

alter table userstories drop FOREIGN KEY fk_assigneeId;

create table pokersession(
  pokerSessionId int PRIMARY KEY AUTO_INCREMENT,
  pokerSessionDate varchar(20) not null,
  pokerSessionStartTime varchar(10) not null,
  userstoryId int not null,
  storyPointTypeId int not null,
  FOREIGN KEY (storyPointTypeId) REFERENCES storypointtype(storyPointTypeId),
  FOREIGN KEY (userstoryId) REFERENCES userstories(userStoryId)
);

alter table estimation drop FOREIGN KEY estimation_ibfk_2;
alter table estimation drop COLUMN userStoryId;

create table estimation(
  userId int not null,
  pokerSessionId int not null,
  pokerPlanningValueId int,
  FOREIGN KEY(userId) REFERENCES users(userID),
  FOREIGN KEY(pokerSessionId) REFERENCES pokersession(pokerSessionId),
  FOREIGN KEY (pokerPlanningValueId) REFERENCES pokerplanningvalues(pokerPlanningValueId),
  PRIMARY KEY(userId,pokerSessionId)
);

drop table estimation;

create table userstoryprogress(
  dayNumber int not null,
  completedUserStoryPercentage int not null,
  userStoryId int not null,
  FOREIGN KEY (userStoryId) REFERENCES userstories(userStoryId),
  PRIMARY KEY(dayNumber,userStoryId)
);

drop table pokersession;

insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('0',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('1/2',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('1',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('2',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('3',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('5',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('8',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('13',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('20',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('40',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('100',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not sure',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not available',1);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('Coffee',1);

insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('0',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('1',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('2',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('3',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('5',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('8',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('13',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('21',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('34',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('55',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('89',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not sure',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not available',2);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('coffee',2);

insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('0',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('extra small',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('small',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('medium',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('large',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('extra large',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('2XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('3XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('4XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('5XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('6XXL',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not sure',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('not available',3);
insert into pokerplanningvalues(pokerPlanningValue, storyPointTypeId) VALUES ('coffee',3);

select * from pokerplanningvalues;
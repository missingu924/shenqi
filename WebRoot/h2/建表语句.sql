
-- -----------------------------------------------------
-- create user
-- -----------------------------------------------------
create user changda password 'changda!@#321' admin；

-- -----------------------------------------------------
-- Table auth_department
-- -----------------------------------------------------
CREATE  TABLE auth_department (
  city VARCHAR(45) ,
  district VARCHAR(45) ,
  departmentId INT NOT NULL ,
  departmentName VARCHAR(128) ,
  parentDepartmentId INT ,
  parentDepartmentName VARCHAR(128) ,
  adminUserAccount VARCHAR(128) ,
  adminUserName VARCHAR(128) ,
  comment VARCHAR(1024) ,
  PRIMARY KEY (departmentId) );


-- -----------------------------------------------------
-- Table auth_function
-- -----------------------------------------------------
CREATE  TABLE auth_function (
  functonId INT NOT NULL ,
  functionName VARCHAR(128) ,
  functionDiscription VARCHAR(512) ,
  PRIMARY KEY (functonId) );


-- -----------------------------------------------------
-- Table auth_group
-- -----------------------------------------------------
CREATE  TABLE auth_group (
  groupId INT NOT NULL ,
  groupName VARCHAR(128) ,
  groupDiscription VARCHAR(512) ,
  PRIMARY KEY (groupId) );


-- -----------------------------------------------------
-- Table auth_group_user
-- -----------------------------------------------------
CREATE  TABLE auth_group_user (
  groupId INT NOT NULL ,
  groupName VARCHAR(128) ,
  userAccount VARCHAR(128) ,
  userName VARCHAR(128) ,
  PRIMARY KEY (groupId) );


-- -----------------------------------------------------
-- Table auth_role
-- -----------------------------------------------------
CREATE  TABLE auth_role (
  roleId INT NOT NULL ,
  roleName VARCHAR(128) ,
  roleDiscription VARCHAR(512) ,
  PRIMARY KEY (roleId) );


-- -----------------------------------------------------
-- Table auth_role_function
-- -----------------------------------------------------
CREATE  TABLE auth_role_function (
  roleId INT NOT NULL ,
  roleName VARCHAR(128) ,
  functionId INT ,
  functionName VARCHAR(128) ,
  PRIMARY KEY (roleId) );


-- -----------------------------------------------------
-- Table auth_user
-- -----------------------------------------------------
CREATE  TABLE auth_user (
  id INT NOT NULL ,
  account VARCHAR(128) NOT NULL ,
  password VARCHAR(128) ,
  name VARCHAR(128) ,
  telephone VARCHAR(128) ,
  sex VARCHAR(128) ,
  province VARCHAR(128) ,
  city VARCHAR(128) ,
  district VARCHAR(128) ,
  departmentId VARCHAR(128) ,
  departmentName VARCHAR(128) ,
  office VARCHAR(128) ,
  rolelevel varchar(128),
  PRIMARY KEY (account, id) ,
  UNIQUE INDEX account_UNIQUE (account ASC) ,
  UNIQUE INDEX id_UNIQUE (id ASC) )
;

-- -----------------------------------------------------
-- Table auth_user_role
-- -----------------------------------------------------
CREATE  TABLE auth_user_role (
	userId VARCHAR(128) ,
	userAccount VARCHAR(128) ,
	roleId VARCHAR(128) ,
	roleName VARCHAR(128) 
	);
	
-- -----------------------------------------------------
-- Table log_login
-- -----------------------------------------------------
CREATE  TABLE log_login (
	id int,
	userAccount VARCHAR(128) ,
	userName VARCHAR(128) ,
	userDistrict VARCHAR(128) ,
	userDepartmentId VARCHAR(128) ,
	userDepartmentName VARCHAR(128) ,
	timeStamp datetime
	);
	
-- -----------------------------------------------------
-- Table config
-- -----------------------------------------------------
CREATE  TABLE system_config (
	key varchar(128),
	value varchar(512)
	);

-- -----------------------------------------------------
-- add admin user
-- -----------------------------------------------------
insert into auth_user (
id,
account,
password,
name,
district,
rolelevel
)
values
(
'0',
'admin',
'admin',
'系统管理员',
'大丰工业（烟台）有限公司',
'系统管理员'
);